package cirucury.client.protocol.impl.kademlia


import java.security.PublicKey

import akka.util.ByteString
import cirucury.client.protocol.base.kademlia.KIOBase.{KMessageIOBaseComponent, PayloadIOBaseComponent}
import cirucury.core.protocol.basic.encoder.UnixTimeEncoderComponent
import cirucury.core.protocol.basic.extractor.UnixTimeExtractorComponent
import cirucury.core.protocol.basic.payload.PayloadIdComponent
import cirucury.core.protocol.kademlia.KMessage.wrap.{KSignMessageComponent, KStrongSignMessageComponent, KWeakSignMessageComponent}
import cirucury.core.protocol.kademlia.KMessage.{KMessageComponent, KPayloadMessageComponent}
import cirucury.core.protocol.kademlia.{IdentifierComponent, MessageIdComponent}
import cirucury.core.protocol.kademlia.io.IOInfoComponent.KNodesResponseInfoComponent
import cirucury.core.protocol.kademlia.io.encoder.KPayloadEncoders.{KPayloadWholeEncoderComponent, request, response}
import cirucury.core.protocol.kademlia.io.encoder._
import cirucury.core.protocol.kademlia.io.extractor.KPayloadExtractors.request.{KFindNodeRequestExtractorComponent, KFindValueRequestExtractorComponent, KPingRequestExtractorComponent, KStoreRequestExtractorComponent}
import cirucury.core.protocol.kademlia.io.extractor.KPayloadExtractors.response.{KNodesResponseExtractorComponent, KSuccessResponseExtractorComponent, KValueResponseExtractorComponent}
import cirucury.core.protocol.kademlia.io.extractor._
import cirucury.core.protocol.kademlia.KPayloads.KPayloadComponent
import cirucury.core.protocol.kademlia.KPayloads.request.{KFindNodeRequestComponent, KFindValueRequestComponent, KPingRequestComponent, KStoreRequestComponent}
import cirucury.core.protocol.kademlia.KPayloads.response.{KNodesResponseComponent, KSuccessResponseComponent, KValueResponseComponent}
import cirucury.core.protocol.kademlia.io.encoder.KMessageEncoders.{KSingleMessageEncoderComponent, StrongSignMessageEncoderComponent, KWeakSignMessageEncoderComponent}
import cirucury.core.protocol.kademlia.io.extractor.KMessageExtractors.{KSingleMessageExtractorComponent, KStrongSignMessageExtractorComponent, KWeakSignMessageExtractorComponent}
import cirucury.core.protocol.kademlia.io.extractor.KPayloadExtractors.KPayloadWholeExtractorComponent
import cirucury.dht.kademlia.KIdComponent

import scala.collection.TraversableOnce
import scala.collection.generic.CanBuildFrom
import scala.language.implicitConversions

object KademliaEEImpl {

  trait KMessageIOMixIn extends KMessageIOBaseComponent
    with KSingleMessageExtractorComponent[ByteString]
    with KWeakSignMessageExtractorComponent[ByteString]
    with KStrongSignMessageExtractorComponent[ByteString]
    with KSingleMessageEncoderComponent[ByteString]
    with KWeakSignMessageEncoderComponent[ByteString]
    with StrongSignMessageEncoderComponent[ByteString] {
    this: KMessageComponent
      with KPayloadMessageComponent
      with KStrongSignMessageComponent
      with KWeakSignMessageComponent
      with KPayloadComponent
      with MessageIdEncoderComponent[TraversableOnce[Byte]]
      with IdentifierComponent
      with IdentifierEncoderComponent[TraversableOnce[Byte]]
      with IdentifierExtractorComponent[ByteString]
      with KPayloadWholeEncoderComponent[TraversableOnce[Byte]]
      with KPayloadWholeExtractorComponent[ByteString]
      with MessageIdComponent
      with UnixTimeEncoderComponent[ByteString]
      with UnixTimeExtractorComponent[ByteString]
      with MessageIdExtractorComponent[TraversableOnce[Byte]]
      with DynamicPuzzleProofExtractorComponent[ByteString]
      with DynamicPuzzleProofEncoderComponent[TraversableOnce[Byte]]
      with KSignMessageComponent
    with PublicKeyExtractorComponent[ByteString]=>
    override val KSingleMessageExtractor = new Extractor[ByteString, KPayloadMessage[_ <: KPayloadLike]] {
      override def unapply(arg: ByteString) = arg.splitAt(MessageIdCompanion.BYTES) match {
        case (MessageIdExtractor(mid), after) if mid == KPayloadSingleMessageId =>
          after.splitAt(IdentifierCompanion.BYTES) match {
            case (IdentifierExtractor(ident), payb) =>
              sized2(payb).flatMap {
                case (KPayloadWholeExtractor(pay), a) if a.isEmpty => Some(KPayloadMessage(ident, pay))
                case _ => None
              }
          }
      }
    }
    override val KWeakSignMessageExtractor = new Extractor[ByteString, KWeakSignMessage[KMessage]] {
      override def unapply(arg: ByteString) = signExtract(KWeakSignMessage)(arg)
    }
    def signExtract[R](r:(Long,KMessage,PublicKey,BigInt,Seq[Byte])=>R)(bs:ByteString): Option[R] ={
      bs.splitAt(MessageIdCompanion.BYTES) match {
        case (MessageIdExtractor(mid), after) if mid == KWeakSignMessageId => after.splitAt(8) match {
          case (UnixTimeExtractor(time), after2) => sized2(after2).flatMap {
            case (KMessageWholeExtractor(mes), after3) => after3.splitAt(KSignMessageCompanion.publicKeyBytes) match {
              case (PublicKeyExtractor(publicKey), after4) =>
                after4.splitAt(16) match {
                  case (DynamicPuzzleProofExtractor(proof),sign)=>Some(r(time, mes, publicKey,proof, sign))
                }
              case _ => None
            }
            case _ => None
          }
        }
        case _ => None
      }
    }
    override val KStrongSignMessageExtractor = new Extractor[ByteString, KStrongSignMessage[KMessage]] {
      override def unapply(arg: ByteString) = signExtract(KStrongSignMessage)(arg)
    }
    override val KMessageWholeExtractor = new Extractor[ByteString, KMessage] {
      override def unapply(arg: ByteString): Option[KMessage] = arg match {
        case KStrongSignMessageExtractor(x) => Some(x)
        case KWeakSignMessageExtractor(x) => Some(x)
        case KSingleMessageExtractor(x) => Some(x)
        case _ => None
      }
    }

    override implicit def KSingleMessageEncode(kademliaSingleMessage: KPayloadMessage[_ <: KPayloadLike]): EncodeResult[ByteString] = new EncodeResult[ByteString] {
      override val bytes = (ByteString.newBuilder ++= kademliaSingleMessage.messageId.bytes ++= kademliaSingleMessage.identifier.bytes ++= sizing2(kademliaSingleMessage.payload.bytes)).result()
    }

    private[this] def SignEncode(signMessage: KSignMessage[_ <: KMessage]): EncodeResult[ByteString] = new EncodeResult[ByteString] {
      override val bytes = (
        ByteString.newBuilder ++= signMessage.messageId.bytes ++= UnixTimeEncode(signMessage.timeStamp).bytes ++= sizing2(signMessage.message.bytes) ++= DynamicPuzzleProofEncode(signMessage.dynamicPuzzleProof).bytes ++= signMessage.sign
        ).result()
    }

    override implicit def WeakSignMessageEncode(weakSignMessage: KWeakSignMessage[_ <: KMessage]): EncodeResult[ByteString] = SignEncode(weakSignMessage)

    override implicit def StrongSignMessageEncode(strongSignMessage: KStrongSignMessage[_ <: KMessage]): EncodeResult[ByteString] = SignEncode(strongSignMessage)

    override implicit def MessageWholeEncode(m: KMessage): EncodeResult[ByteString] = {
      m match {
        case x: KPayloadMessage[_] => x
        case x: KWeakSignMessage[_] => x
        case x: KStrongSignMessage[_] => x
        case _ => throw new IllegalArgumentException
      }
    }
  }

  trait PayloadIOMixIn extends PayloadIOBaseComponent
    with KFindNodeRequestExtractorComponent[ByteString]
    with KFindValueRequestExtractorComponent[ByteString]
    with KStoreRequestExtractorComponent[ByteString]
    with KPingRequestExtractorComponent[ByteString]
    with KValueResponseExtractorComponent[ByteString]
    with KSuccessResponseExtractorComponent[ByteString]
    with KNodesResponseExtractorComponent[ByteString]
    with request.KFindNodeRequestEncoderComponent[ByteString]
    with request.KPingRequestEncoderComponent[ByteString]
    with request.KStoreRequestEncoderComponent[ByteString]
    with request.KFindValueRequestEncoderComponent[ByteString]
    with response.KNodesResponseEncoderComponent[ByteString]
    with response.KSuccessResponseEncoderComponent[ByteString]
    with response.KValueResponseEncoderComponent[ByteString] {
    this: KNodesResponseComponent
      with KSuccessResponseComponent
      with KValueResponseComponent
      with KPingRequestComponent
      with KStoreRequestComponent
      with KFindValueRequestComponent
      with KFindNodeRequestComponent
      with PayloadIdComponent
      with PayloadIdEncoderComponent[TraversableOnce[Byte]]
      with PayloadIdExtractorComponent[ByteString]
      with ValueEncoderComponent[TraversableOnce[Byte]]
      with ValueExtractorComponent[ByteString]
      with KNodeInfoEncoderComponent[TraversableOnce[Byte]]
      with KNodeInfoExtractorComponent[ByteString]
      with KIdComponent
      with KNodesResponseInfoComponent =>

    override implicit def KFindNodeRequestEncode(findNodeRequest: KFindNodeRequest): EncodeResult[ByteString] = new EncodeResult[ByteString] {
      override val bytes = (ByteString.newBuilder ++= findNodeRequest.payloadId.bytes ++= findNodeRequest.target.bytes).result()
    }

    override implicit def KPingRequestEncode(pingRequest: KPingRequest): EncodeResult[ByteString] = new EncodeResult[ByteString] {
      override val bytes = (ByteString.newBuilder ++= pingRequest.payloadId.bytes).result()
    }

    override implicit def KStoreRequestEncode(storeRequest: KStoreRequest[_ <: ValueR]): EncodeResult[ByteString] = new EncodeResult[ByteString] {
      override val bytes = (ByteString.newBuilder ++= storeRequest.payloadId.bytes ++= storeRequest.key.bytes ++= sizing2(storeRequest.value.bytes)).result()
    }

    override implicit def KFindValueRequestEncode(findValueRequest: KFindValueRequest): EncodeResult[ByteString] = new EncodeResult[ByteString] {
      override val bytes = (ByteString.newBuilder ++= findValueRequest.payloadId.bytes ++= findValueRequest.target.bytes).result()
    }

    override implicit def KNodesResponseEncode(nodesResponse: KNodesResponse): EncodeResult[ByteString] = new EncodeResult[ByteString] {
      override val bytes = (ByteString.newBuilder ++= nodesResponse.payloadId.bytes ++= nodesResponse.kNodeInfo.flatMap(_.bytes)).result()
    }

    override implicit def KSuccessResponseEncode(successResponse: KSuccessResponse): EncodeResult[ByteString] = new EncodeResult[ByteString] {
      override val bytes = (ByteString.newBuilder ++= successResponse.payloadId.bytes).result()
    }

    override implicit def KValueResponseEncode(valueResponse: KValueResponse[_ <: ValueR]): EncodeResult[ByteString] = new EncodeResult[ByteString] {
      override val bytes = (ByteString.newBuilder ++= valueResponse.payloadId.bytes ++= sizing2(valueResponse.value.bytes)).result()
    }

    override implicit def KPayloadWholeEncode(kademliaPayloadLike: KPayloadLike): EncodeResult[ByteString] = kademliaPayloadLike match {
      case x: KFindNodeRequest => x
      case x: KFindValueRequest => x
      case x: KPingRequest => x
      case x: KStoreRequest[_] => x
      case x: KNodesResponse => x
      case x: KSuccessResponse => x
      case x: KValueResponse[_] => x
    }

    override val KPingRequestExtractor = new Extractor[ByteString, KPingRequest] {
      override def unapply(arg: ByteString) = {
        arg.take(PayloadIdCompanion.BYTES) match {
          case PayloadIdExtractor(pid) if pid == KPingRequestPayloadId => Some(KPingRequest)
          case _ => None
        }
      }
    }

    override val KValueResponseExtractor = new Extractor[ByteString, KValueResponse[ValueR]] {
      override def unapply(arg: ByteString) = arg.splitAt(PayloadIdCompanion.BYTES) match {
        case (PayloadIdExtractor(pid), after) if pid == KValueResponsePayloadId =>
          sized2(after).flatMap { case (ValueExtractor(v), a) if a.isEmpty => Some(KValueResponse(v)) }
        case _ => None
      }
    }

    override val KFindNodeRequestExtractor = new Extractor[ByteString, KFindNodeRequest] {
      override def unapply(arg: ByteString) = arg.splitAt(PayloadIdCompanion.BYTES) match {
        case (PayloadIdExtractor(pid), targetb) if pid == KFindNodeRequestPayloadId =>
          val target = KId(targetb)
          Some(KFindNodeRequest(target))
        case _ => None
      }
    }


    override val KNodesResponseExtractor = new Extractor[ByteString, KNodesResponse] {
      override def unapply(arg: ByteString) = arg.splitAt(PayloadIdCompanion.BYTES) match {
        case (PayloadIdExtractor(pid), after) if pid == KNodesResponsePayloadId =>
          Some(KNodesResponse(after.sliding(KNodesResponseInfo.BYTES).flatMap(KNodeInfoExtractor.unapply).toSeq))
        case _ => None
      }
    }


    override val KStoreRequestExtractor = new Extractor[ByteString, KStoreRequest[ValueR]] {
      override def unapply(arg: ByteString) = arg.splitAt(PayloadIdCompanion.BYTES) match {
        case (PayloadIdExtractor(pid), after) if pid == KStoreRequestPayloadId => after.splitAt(KIdCompanion.BYTES) match {
          case (kidb, ValueExtractor(v)) =>
            Some(KStoreRequest(KId(kidb), v))
          case _ => None
        }
        case _ => None
      }
    }

    override val KSuccessResponseExtractor = new Extractor[ByteString, KSuccessResponse] {
      override def unapply(arg: ByteString) = arg match {
        case PayloadIdExtractor(pid) if pid == KSuccessResponsePayloadId => Some(KSuccessResponse)
        case _ => None
      }
    }
    override val KFindValueRequestExtractor = new Extractor[ByteString, KFindValueRequest] {
      override def unapply(arg: ByteString) = arg.splitAt(PayloadIdCompanion.BYTES) match {
        case (PayloadIdExtractor(pid), targetb) if pid == KFindValueRequestPayloadId =>
          Some(KFindValueRequest(KId(targetb)))
        case _ => None
      }
    }
    override val KPayloadWholeExtractor = new Extractor[ByteString, KPayloadLike] {
      override def unapply(arg: ByteString) = arg match {
        case KPingRequestExtractor(x) => Some(x)
        case KValueResponseExtractor(x) => Some(x)
        case KFindNodeRequestExtractor(x) => Some(x)
        case KNodesResponseExtractor(x) => Some(x)
        case KStoreRequestExtractor(x) => Some(x)
        case KSuccessResponseExtractor(x) => Some(x)
        case KFindValueRequestExtractor(x) => Some(x)
        case _ => None
      }
    }
  }

  private[this] def sizing2[T <: TraversableOnce[Byte]](src: T)(implicit cbf: CanBuildFrom[Nothing, Byte, T]): T = (cbf() += (src.size << 8).toByte += src.size.toByte ++= src).result()

  private[this] def sized2[T <: Traversable[Byte]](src: T)(implicit cbf: CanBuildFrom[Traversable[Byte], Byte, T]): Option[(T, T)] = src.splitAt(2) match {
    case (lb, r) =>
      Some(r.splitAt(lb.foldLeft(0) { case (a, b) => (a << 8) & b })).map { case (b, a) => (cbf(b).result(), cbf(a).result()) }
  }
}

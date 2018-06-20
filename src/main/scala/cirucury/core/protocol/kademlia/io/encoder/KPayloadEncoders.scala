package cirucury.core.protocol.kademlia.io.encoder

import cirucury.core.protocol.basic.encoder.EncoderComponent
import cirucury.core.protocol.kademlia.KPayloads.request.{KFindNodeRequestComponent, KFindValueRequestComponent, KPingRequestComponent, KStoreRequestComponent}
import cirucury.core.protocol.kademlia.KPayloads.response.{KNodesResponseComponent, KSuccessResponseComponent, KValueResponseComponent}

import scala.language.implicitConversions

object KPayloadEncoders {

  object request {

    trait KFindNodeRequestEncoderComponent[+R] extends EncoderComponent {
      this: KFindNodeRequestComponent =>
      implicit def KFindNodeRequestEncode(findNodeRequest: KFindNodeRequest): EncodeResult[R]
    }

    trait KFindValueRequestEncoderComponent[+R] extends EncoderComponent {
      this: KFindValueRequestComponent =>
      implicit def KFindValueRequestEncode(findValueRequest: KFindValueRequest): EncodeResult[R]
    }

    trait KPingRequestEncoderComponent[+R] extends EncoderComponent {
      this: KPingRequestComponent =>
      implicit def KPingRequestEncode(pingRequest: KPingRequest): EncodeResult[R]
    }

    trait KStoreRequestEncoderComponent[+R] extends EncoderComponent {
      this: KStoreRequestComponent =>
      implicit def KStoreRequestEncode(storeRequest: KStoreRequest[_ <: ValueR]): EncodeResult[R]
    }

  }

  object response {

    trait KNodesResponseEncoderComponent[+R] extends EncoderComponent{
      this: KNodesResponseComponent =>
      implicit def KNodesResponseEncode(nodesResponse: KNodesResponse): EncodeResult[R]
    }

    trait KSuccessResponseEncoderComponent[+R] extends EncoderComponent {
      this: KSuccessResponseComponent =>
      implicit def KSuccessResponseEncode(successResponse: KSuccessResponse): EncodeResult[R]
    }

    trait KValueResponseEncoderComponent[+R] extends EncoderComponent {
      this: KValueResponseComponent =>
      implicit def KValueResponseEncode(valueResponse: KValueResponse[_ <: ValueR]): EncodeResult[R]
    }

  }

  trait KPayloadWholeEncoderComponent[+R] extends EncoderComponent {
    this: KFindNodeRequestComponent
      with KFindValueRequestComponent
      with KValueResponseComponent
      with KPingRequestComponent
      with KStoreRequestComponent
      with KNodesResponseComponent
      with KSuccessResponseComponent
      with request.KFindNodeRequestEncoderComponent[R]
      with request.KPingRequestEncoderComponent[R]
      with request.KStoreRequestEncoderComponent[R]
      with request.KFindValueRequestEncoderComponent[R]
      with response.KNodesResponseEncoderComponent[R]
      with response.KSuccessResponseEncoderComponent[R]
      with response.KValueResponseEncoderComponent[R]=>
    implicit def KPayloadWholeEncode(kademliaPayloadLike: KPayloadLike): EncodeResult[R]
  }

}

package cirucury.client.protocol.base.kademlia

import akka.util.ByteString
import cirucury.core.protocol.kademlia.KMessage.KMessageComponent
import cirucury.core.protocol.kademlia.io.encoder.KPayloadEncoders.{KPayloadWholeEncoderComponent, request, response}
import cirucury.core.protocol.kademlia.io.extractor.KPayloadExtractors.KPayloadWholeExtractorComponent
import cirucury.core.protocol.kademlia.io.extractor.KPayloadExtractors.request.{KFindNodeRequestExtractorComponent, KFindValueRequestExtractorComponent, KPingRequestExtractorComponent, KStoreRequestExtractorComponent}
import cirucury.core.protocol.kademlia.io.extractor.KPayloadExtractors.response.{KNodesResponseExtractorComponent, KSuccessResponseExtractorComponent, KValueResponseExtractorComponent}
import cirucury.core.protocol.kademlia.KPayloads.request.{KFindNodeRequestComponent, KFindValueRequestComponent, KPingRequestComponent, KStoreRequestComponent}
import cirucury.core.protocol.kademlia.KPayloads.response.{KNodesResponseComponent, KSuccessResponseComponent, KValueResponseComponent}
import cirucury.core.protocol.kademlia.io.encoder.KMessageEncoders.{KSingleMessageEncoderComponent, MessageWholeEncoderComponent, StrongSignMessageEncoderComponent, KWeakSignMessageEncoderComponent}
import cirucury.core.protocol.kademlia.io.extractor.KMessageExtractors.{KSingleMessageExtractorComponent, KMessageWholeExtractorComponent, KStrongSignMessageExtractorComponent, KWeakSignMessageExtractorComponent}

object KIOBase {
  trait KMessageIOBaseComponent extends MessageWholeEncoderComponent[ByteString] with KMessageWholeExtractorComponent[ByteString] {
    this: KMessageComponent
      with KSingleMessageExtractorComponent[ByteString]
      with KWeakSignMessageExtractorComponent[ByteString]
      with KStrongSignMessageExtractorComponent[ByteString]
      with KSingleMessageEncoderComponent[ByteString]
      with KWeakSignMessageEncoderComponent[ByteString]
      with StrongSignMessageEncoderComponent[ByteString] =>
  }

  trait PayloadIOBaseComponent extends KPayloadWholeExtractorComponent[ByteString] with KPayloadWholeEncoderComponent[ByteString] {
    this: KNodesResponseComponent
      with KSuccessResponseComponent
      with KValueResponseComponent
      with KPingRequestComponent
      with KStoreRequestComponent
      with KFindValueRequestComponent
      with KFindNodeRequestComponent
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
      with response.KValueResponseEncoderComponent[ByteString] =>
  }

}

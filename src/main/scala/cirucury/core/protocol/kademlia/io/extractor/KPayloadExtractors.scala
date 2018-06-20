package cirucury.core.protocol.kademlia.io.extractor

import cirucury.core.protocol.basic.extractor.ExtractorComponent
import cirucury.core.protocol.kademlia.io.extractor.KPayloadExtractors.request.{KFindNodeRequestExtractorComponent, KFindValueRequestExtractorComponent, KPingRequestExtractorComponent, KStoreRequestExtractorComponent}
import cirucury.core.protocol.kademlia.io.extractor.KPayloadExtractors.response.{KNodesResponseExtractorComponent, KSuccessResponseExtractorComponent, KValueResponseExtractorComponent}
import cirucury.core.protocol.kademlia.KPayloads.request.{KFindNodeRequestComponent, KFindValueRequestComponent, KPingRequestComponent, KStoreRequestComponent}
import cirucury.core.protocol.kademlia.KPayloads.response.{KNodesResponseComponent, KSuccessResponseComponent, KValueResponseComponent}

object KPayloadExtractors {
  object request{
    trait KFindNodeRequestExtractorComponent[-S] extends ExtractorComponent{
      this:KFindNodeRequestComponent=>
      val KFindNodeRequestExtractor:Extractor[S,KFindNodeRequest]
    }
    trait KFindValueRequestExtractorComponent[-S] extends ExtractorComponent{
      this:KFindValueRequestComponent=>
      val KFindValueRequestExtractor:Extractor[S,KFindValueRequest]
    }
    trait KStoreRequestExtractorComponent[-S] extends ExtractorComponent{
      this:KStoreRequestComponent=>
      val KStoreRequestExtractor:Extractor[S,KStoreRequest[ValueR]]
    }
    trait KPingRequestExtractorComponent[-S] extends ExtractorComponent{
      this:KPingRequestComponent=>
      val KPingRequestExtractor:Extractor[S,KPingRequest]
    }
  }

  object response{
    trait KValueResponseExtractorComponent[-S] extends ExtractorComponent{
      this:KValueResponseComponent=>
      val KValueResponseExtractor:Extractor[S,KValueResponse[ValueR]]
    }
    trait KSuccessResponseExtractorComponent[-S] extends ExtractorComponent{
      this:KSuccessResponseComponent=>
      val KSuccessResponseExtractor:Extractor[S,KSuccessResponse]
    }
    trait KNodesResponseExtractorComponent[-S] extends ExtractorComponent{
      this:KNodesResponseComponent=>
      val KNodesResponseExtractor:Extractor[S,KNodesResponse]
    }
  }
  trait KPayloadWholeExtractorComponent[-S] {
    this: KFindNodeRequestExtractorComponent[S]
      with KFindValueRequestExtractorComponent[S]
      with KStoreRequestExtractorComponent[S]
      with KPingRequestExtractorComponent[S]
      with KValueResponseExtractorComponent[S]
      with KSuccessResponseExtractorComponent[S]
      with KNodesResponseExtractorComponent[S]
      with KNodesResponseComponent
      with KSuccessResponseComponent
      with KValueResponseComponent
      with KPingRequestComponent
      with KStoreRequestComponent
      with KFindValueRequestComponent
      with KFindNodeRequestComponent =>
    val KPayloadWholeExtractor:Extractor[S,KPayloadLike]
  }
}

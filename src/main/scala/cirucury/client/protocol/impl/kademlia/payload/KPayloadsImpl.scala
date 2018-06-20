package cirucury.client.protocol.impl.kademlia.payload

import cirucury.core.protocol.basic.payload.PayloadIdComponent
import cirucury.core.protocol.kademlia.KPayloads.KHaveValuePayloadComponent
import cirucury.core.protocol.kademlia.KPayloads.request.{KFindNodeRequestComponent, KFindValueRequestComponent, KPingRequestComponent, KStoreRequestComponent}
import cirucury.core.protocol.kademlia.KPayloads.response.{KNodesResponseComponent, KSuccessResponseComponent, KValueResponseComponent}
import cirucury.dht.kademlia.{KIdComponent, KNodeInfoComponent}

object KPayloadsImpl {
  object request {

    trait KPingRequestMixIn extends KPingRequestComponent {
      this: PayloadIdComponent =>
      override val KPingRequest = new KPingRequest {}
    }

    trait KStoreRequestMixIn extends KStoreRequestComponent{
      this:PayloadIdComponent with KIdComponent=>
      override def KStoreRequest[Value <: ValueR](_key: KId, _value: Value) = new KStoreRequest[Value]{
        override val key = _key
        override val value = _value
      }
    }

    trait KFindValueRequestMixIn extends KFindValueRequestComponent{
      this:KIdComponent with PayloadIdComponent=>
      override def KFindValueRequest(_target: KId) = new KFindValueRequest{
        override val target = _target
      }
    }
    trait KFindNodeRequestMixIn extends KFindNodeRequestComponent{
      this:PayloadIdComponent with KIdComponent=>
      override def KFindNodeRequest(_target: KId) = new KFindNodeRequest {
        override val target = _target
      }
    }
  }
  object response {

    trait KSuccessResponseMixIn extends KSuccessResponseComponent {
      this: PayloadIdComponent =>
      override val KSuccessResponse = new KSuccessResponse {}
    }

    trait KValueResponseMixIn extends KValueResponseComponent {
      this: PayloadIdComponent with KHaveValuePayloadComponent =>
      override def KValueResponse[Value <: ValueR](v: Value) = new KValueResponse[Value] {
        override val value = v
      }
    }

    trait KNodesResponseMixIn extends KNodesResponseComponent {
      this: KNodeInfoComponent with PayloadIdComponent =>
      override def KNodesResponse(_kNodeInfo: Seq[KNodeInfo]) = new KNodesResponse {
        override val kNodeInfo = _kNodeInfo
      }
    }

  }


}

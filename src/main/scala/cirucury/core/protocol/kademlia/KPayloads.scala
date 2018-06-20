package cirucury.core.protocol.kademlia

import cirucury.core.protocol.basic.payload.{PayloadComponent, PayloadIdComponent}
import cirucury.dht.kademlia.{KIdComponent, KNodeInfoComponent}

object KPayloads{
   trait KPayloadComponent extends PayloadComponent{
    this: PayloadIdComponent=>
    sealed trait KPayloadLike extends PayloadLike
  }
  trait KHaveValuePayloadComponent extends KPayloadComponent{
    this: PayloadIdComponent=>
    sealed trait KHaveValuePayloadLike[+Value<:ValueR] extends KPayloadLike{
      val value:Value
    }
    type ValueR
  }
  object response {

    trait KValueResponseComponent extends KHaveValuePayloadComponent {
      this: PayloadIdComponent =>

      trait KValueResponse[+Value<:ValueR] extends KHaveValuePayloadLike[Value] {
        final override val payloadId = KValueResponsePayloadId
      }

      val KValueResponsePayloadId: PayloadId

      def KValueResponse[Value<:ValueR](v: Value): KValueResponse[Value]
    }

    trait KSuccessResponseComponent extends KPayloadComponent {
      this:   PayloadIdComponent =>

      trait KSuccessResponse extends KPayloadLike {
        final override val payloadId = KSuccessResponsePayloadId
      }

      val KSuccessResponse: KSuccessResponse

      val KSuccessResponsePayloadId: PayloadId
    }

    trait KNodesResponseComponent extends KPayloadComponent {
      this:  PayloadIdComponent with KNodeInfoComponent =>

      trait KNodesResponse extends KPayloadLike {
        val kNodeInfo: Seq[KNodeInfo]
        final override val payloadId = KNodesResponsePayloadId
      }

      val KNodesResponsePayloadId: PayloadId

      def KNodesResponse(_kNodeInfo: Seq[KNodeInfo]): KNodesResponse
    }

  }
  object request {

    trait KStoreRequestComponent extends KHaveValuePayloadComponent {
      this:  PayloadIdComponent with KIdComponent  =>

      trait KStoreRequest[+Value<:ValueR] extends KHaveValuePayloadLike[Value] {
        val key: KId
        final override val payloadId = KStoreRequestPayloadId
      }

      val KStoreRequestPayloadId: PayloadId

      def KStoreRequest[Value<:ValueR](_key: KId, _value: Value): KStoreRequest[Value]
    }

    trait KPingRequestComponent extends KPayloadComponent {
      this: PayloadIdComponent  =>

      trait KPingRequest extends KPayloadLike {
        final override val payloadId = KPingRequestPayloadId
      }

      val KPingRequestPayloadId: PayloadId

      val KPingRequest: KPingRequest
    }

    trait KFindValueRequestComponent extends KFindRequestBaseComponent {
      this: KIdComponent with PayloadIdComponent =>

      /**
        * https://nazenani-torrent.firefirestyle.net/dht/FindNodes.html
        */
      trait KFindValueRequest extends KFindRequestLike {
        final override val payloadId = KFindValueRequestPayloadId
      }

      def KFindValueRequest(_target: KId): KFindValueRequest

      val KFindValueRequestPayloadId: PayloadId
    }

    trait KFindRequestBaseComponent extends KPayloadComponent {
      this: PayloadIdComponent with KIdComponent  =>

      sealed trait KFindRequestLike extends KPayloadLike {
        val target: KId
      }

    }

    trait KFindNodeRequestComponent extends KFindRequestBaseComponent {
      this:  KIdComponent with PayloadIdComponent =>

      /**
        * https://nazenani-torrent.firefirestyle.net/dht/FindNodes.html
        */
      trait KFindNodeRequest extends KFindRequestLike {
        final override val payloadId = KFindNodeRequestPayloadId
      }

      def KFindNodeRequest(_target: KId): KFindNodeRequest

      val KFindNodeRequestPayloadId: PayloadId
    }

  }

}

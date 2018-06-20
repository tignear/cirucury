package cirucury.client.stream

import akka.NotUsed
import akka.stream._
import akka.stream.scaladsl.{Balance, Broadcast, Flow, GraphDSL, Sink, UnzipWith}
import cirucury.core.protocol.kademlia.KMessage.wrap.{KStrongSignMessageComponent, KWeakSignMessageComponent}
import cirucury.core.protocol.kademlia.KMessage.{KMessageComponent, KPayloadMessageComponent}
import cirucury.core.protocol.kademlia.KPayloads.KPayloadComponent
import cirucury.core.protocol.kademlia.{KAddressComponent, KEnvelopeComponent}

object Stream {
  trait ReceiveKademliaEnvelopeRouterUse{
    this:KEnvelopeComponent with KAddressComponent with KMessageComponent
    with KPayloadMessageComponent with KWeakSignMessageComponent with KPayloadComponent with KStrongSignMessageComponent=>
    val KEnvelopeRouterStream:Graph[FanOutShape3[KEnvelope[KAddress,KMessage],
      KEnvelope[KAddress,KPayloadMessage[KPayloadLike]],
      KEnvelope[KAddress,KWeakSignMessage[KMessage]],
      KEnvelope[KAddress,KStrongSignMessage[KMessage]]],NotUsed] ={
      val broadcast=Broadcast[KEnvelope[KAddress,KMessage]](3)
      val singleMCollector=Flow[KEnvelope[KAddress,KMessage]].collect({
        case m:KEnvelope[KAddress,KPayloadMessage[KPayloadLike]]=>m
      })
      val weakMCollector=Flow[KEnvelope[KAddress,KMessage]].collect({
        case m:KEnvelope[KAddress,KWeakSignMessage[KMessage]]=>m
      })
      val strongMCollector=Flow[KEnvelope[KAddress,KMessage]].collect({
        case m:KEnvelope[KAddress,KStrongSignMessage[KMessage]]=>m
      })
      import GraphDSL.Implicits._
      GraphDSL.create() { implicit builder =>
        val IN=builder.add(broadcast)
        val C1=builder.add(singleMCollector)
        val C2=builder.add(weakMCollector)
        val C3=builder.add(strongMCollector)
        IN ~> C1
        IN ~> C2
        IN ~> C3
        new FanOutShape3(IN.in,C1.out,C2.out,C3.out)
      }
    }
  }

}

package cirucury.core.protocol.kademlia

import java.net.{Inet4Address, Inet6Address, InetSocketAddress}

import cirucury.core.protocol.basic.EnvelopeComponent
import cirucury.core.protocol.kademlia.KMessage.KMessageComponent
import cirucury.dht.kademlia.KIdComponent


trait KEnvelopeComponent extends EnvelopeComponent{
  this:KMessageComponent with KAddressComponent with KIdComponent=>
  trait KEnvelope[+A<:KAddress,+M<:KMessage] extends Envelope[A,M]
  def KEnvelope(from6:Option[Inet6Address], from4:Option[Inet4Address], fromPort:Short, to:InetSocketAddress, content:KMessage):KEnvelope[KAddress,KMessage]
}

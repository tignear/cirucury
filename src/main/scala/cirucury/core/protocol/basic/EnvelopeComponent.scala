package cirucury.core.protocol.basic

import java.net.{InetAddress, InetSocketAddress}

import cirucury.core.protocol.basic.payload.PayloadComponent

trait EnvelopeComponent{
  this:AddressComponent=>
  trait Envelope[+Address<:AddressBase,+Content]{
    val content:Content
    val address:Address
  }
}

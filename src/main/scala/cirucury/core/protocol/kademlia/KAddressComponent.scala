package cirucury.core.protocol.kademlia


import cirucury.core.protocol.basic.AddressComponent
import cirucury.dht.kademlia.KIdComponent

trait KAddressComponent extends AddressComponent{
  this:KIdComponent=>
  trait KFromInfo extends FromInfo{
    val fromId:KId
  }
  trait KFromWInfo extends KFromInfo with FromWInfo
  trait KFromV6Info extends KFromInfo with FromV6Info
  trait KFromV4Info extends KFromInfo with FromV4Info
  trait KAddress extends AddressBase{
    override val from :KFromInfo
  }
}

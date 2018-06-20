package cirucury.dht.kademlia

import java.net.{Inet4Address, Inet6Address}
trait KNodeInfoComponent {
  this:KIdComponent=>
  type KNodeInfo<:KNodeInfoLike
  trait KNodeInfoLike{
    val peerId: KId
    val ipv4:Inet4Address
    val ipv6:Inet6Address
    val port:Short
  }

  def KNodeInfo(peerId: KId, ipv4:Inet4Address,ipv6:Inet6Address,port:Short):KNodeInfo

}
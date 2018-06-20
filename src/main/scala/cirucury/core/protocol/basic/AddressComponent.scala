package cirucury.core.protocol.basic

import java.net.{Inet4Address, Inet6Address, InetAddress, InetSocketAddress}

trait AddressComponent {
  trait FromInfo{
    val inet: InetAddress
    val port: Short
  }
  trait FromWInfo extends FromInfo{
    override val inet:Inet6Address
    val inetOther:Inet4Address
  }
  trait AddressBase{
    val from:FromInfo
    val to:ToInfo
  }
  trait ToInfo{
    val to: InetSocketAddress
  }
  trait FromV6Info extends FromInfo{
    override val inet:Inet6Address
  }
  trait FromV4Info extends FromInfo{
    override val inet :Inet4Address
  }
}

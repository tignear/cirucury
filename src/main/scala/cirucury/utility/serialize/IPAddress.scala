package cirucury.utility.serialize

import java.net.{Inet6Address, InetAddress, InetSocketAddress}
import java.nio.ByteOrder

import scala.language.implicitConversions

object IPAddress {
  private def ipv6b(a: InetAddress): Seq[Byte] = {
    val ba = a.getAddress
    val bz: Byte = 0
    if (ba.length == 16) ba else Seq[Byte](bz, bz, bz, bz, bz, bz, bz, bz, bz, bz, 0xff, 0xff) ++ ba
  }
  implicit class ConvertAddress(src:InetAddress) {
    def ipv6:Inet6Address=src match {
      case e:Inet6Address=>e
      case _=>InetAddress.getByAddress(ipv6b(src).toArray).asInstanceOf[Inet6Address]
    }
  }
  implicit class BytesAddress(src:InetAddress){
    def bytes:Seq[Byte]=ipv6b(src)
  }
  implicit class BytesSocketAddress(src:InetSocketAddress){
    def bytes:Seq[Byte]=ipv6b(src.getAddress)++src.getPort.bytes
  }
  implicit class BytesInt(src: Int) {
    def bytes[A>:Seq[Byte]](order:ByteOrder,factory:(Byte,Byte,Byte,Byte)=>A=(a:Byte,b:Byte,c:Byte,d:Byte)=>Seq(a,b,c,d)):A={
      if(order==ByteOrder.BIG_ENDIAN)factory(src>>>24,src>>>16,src>>>8,src) else factory(src,src>>>8,src>>>16,src>>>24)
    }
    def bytes:Seq[Byte]=bytes(ByteOrder.BIG_ENDIAN)
  }
  private implicit def IntToByte(src:Int): Byte =src.toByte
}

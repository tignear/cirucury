package cirucury.client.protocol.impl.basic.encoder

import java.net.{Inet4Address, Inet6Address, InetAddress, InetSocketAddress}

import akka.util.ByteString
import cirucury.core.protocol.basic.encoder.{Inet4AddressEncoderComponent, Inet6AddressEncoderComponent, InetAddressEncoderComponent, InetSocketAddressEncoderComponent}
import cirucury.utility.MChain._
import scala.language.implicitConversions


trait InetAddressMappingEncoderMixIn extends InetSocketAddressEncoderComponent[ByteString] with InetAddressEncoderComponent[ByteString] with Inet6AddressEncoderComponent[Array[Byte]] with Inet4AddressEncoderComponent[List[Byte]]{
  override implicit def InetAddressEncoder(inetAddress: InetAddress): EncodeResult[ByteString] = new EncodeResult[ByteString]{
    override def bytes = ({ByteString.newBuilder andAction(_.sizeHint(16))}++=(inetAddress match {
      case v4:Inet4Address=>v4.bytes
      case v6:Inet6Address=>v6.bytes
    })).result()
  }

  override implicit def Inet6Encoder(inet: Inet6Address): EncodeResult[Array[Byte]] = new EncodeResult[Array[Byte]] {
    override def bytes = inet.getAddress
  }
  override implicit def Inet4Encoder(inet: Inet4Address): EncodeResult[List[Byte]] = new EncodeResult[List[Byte]] {
    private val _00= 0.toByte
    private val _FF=0xff.toByte
    override def bytes = _00::_00::_00::_00::_00::_00::_00::_00::_00::_00::_FF::_FF::Nil:::inet.getAddress.toList
  }

  override implicit def InetSocketAddressEncoder(inetSocketAddress: InetSocketAddress): EncodeResult[ByteString] = new EncodeResult[ByteString] {
    private[this] val port=inetSocketAddress.getPort
    override def bytes =InetAddressEncoder(inetSocketAddress.getAddress).bytes:+(port>>8).toByte:+port.toByte
  }
}

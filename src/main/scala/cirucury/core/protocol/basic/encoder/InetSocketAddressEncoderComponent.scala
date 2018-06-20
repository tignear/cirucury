package cirucury.core.protocol.basic.encoder

import java.net.InetSocketAddress
import scala.language.implicitConversions

trait InetSocketAddressEncoderComponent[+R] extends EncoderComponent{
  implicit def InetSocketAddressEncoder(inetSocketAddress: InetSocketAddress):EncodeResult[R]
}

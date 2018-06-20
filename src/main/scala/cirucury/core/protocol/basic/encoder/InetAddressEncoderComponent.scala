package cirucury.core.protocol.basic.encoder

import java.net.InetAddress
import scala.language.implicitConversions

trait InetAddressEncoderComponent[+R] extends EncoderComponent{
  implicit def InetAddressEncoder(inetAddress:InetAddress):EncodeResult[R]
}

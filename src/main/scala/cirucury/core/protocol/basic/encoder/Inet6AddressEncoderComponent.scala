package cirucury.core.protocol.basic.encoder

import java.net.Inet6Address
import scala.language.implicitConversions

trait Inet6AddressEncoderComponent[+R] extends EncoderComponent{
  implicit def Inet6Encoder(inet:Inet6Address):EncodeResult[R]
}

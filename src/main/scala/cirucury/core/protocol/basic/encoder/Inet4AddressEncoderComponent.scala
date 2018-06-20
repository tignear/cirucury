package cirucury.core.protocol.basic.encoder
import java.net.Inet4Address
import scala.language.implicitConversions


trait Inet4AddressEncoderComponent[+R] extends EncoderComponent{
  implicit def Inet4Encoder(inet:Inet4Address):EncodeResult[R]
}


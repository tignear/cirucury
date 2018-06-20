package cirucury.core.protocol.basic.encoder

import cirucury.core.protocol.basic.VersionComponent
import scala.language.implicitConversions

trait VersionEncoderComponent[+R] extends EncoderComponent{
  this:VersionComponent=>
  implicit def VersionEncoder(version: Version):EncodeResult[R]
}

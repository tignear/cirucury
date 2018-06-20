package cirucury.client.protocol.base.basic.encoder

import cirucury.core.protocol.basic.IntVersionMixIn
import cirucury.core.protocol.basic.encoder.{IntEncoderComponent, VersionEncoderComponent}
import scala.language.implicitConversions

trait IntVersionEncoderDelegateComponent[+R] extends VersionEncoderComponent[R] with IntVersionMixIn{
  this:IntEncoderComponent[R]=>
  override implicit def VersionEncoder(version: Version): EncodeResult[R] = IntEncoder(version)
}
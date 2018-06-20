package cirucury.core.protocol.kademlia.io.encoder

import cirucury.core.protocol.basic.encoder.EncoderComponent
import cirucury.core.protocol.kademlia.IdentifierComponent

trait IdentifierEncoderComponent[+R] extends EncoderComponent{
  this:IdentifierComponent=>
  implicit def IdentifierEncode(identifier: Identifier):EncodeResult[R]
}

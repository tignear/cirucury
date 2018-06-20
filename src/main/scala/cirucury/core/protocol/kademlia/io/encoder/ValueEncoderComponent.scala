package cirucury.core.protocol.kademlia.io.encoder

import cirucury.core.protocol.basic.encoder.EncoderComponent
import cirucury.core.protocol.kademlia.KPayloads.KHaveValuePayloadComponent

trait ValueEncoderComponent[+R] extends EncoderComponent{
  this:KHaveValuePayloadComponent =>
  implicit def ValueEncode(value: ValueR):EncodeResult[R]
}

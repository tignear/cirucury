package cirucury.core.protocol.kademlia.io.encoder

import cirucury.core.protocol.basic.encoder.EncoderComponent
import cirucury.core.protocol.basic.payload.PayloadIdComponent

trait PayloadIdEncoderComponent[+R] extends EncoderComponent{
  this:PayloadIdComponent=>
  implicit def PayloadIdEncode(payloadId: PayloadId):EncodeResult[R]
}

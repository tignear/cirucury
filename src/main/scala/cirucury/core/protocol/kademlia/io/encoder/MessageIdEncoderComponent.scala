package cirucury.core.protocol.kademlia.io.encoder

import cirucury.core.protocol.basic.encoder.EncoderComponent
import cirucury.core.protocol.kademlia.MessageIdComponent

trait MessageIdEncoderComponent[+R] extends EncoderComponent{
  this:MessageIdComponent=>
  implicit def MessageIdEncode(messageId: MessageId):EncodeResult[R]
}

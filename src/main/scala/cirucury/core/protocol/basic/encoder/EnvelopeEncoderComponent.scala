package cirucury.core.protocol.basic.encoder

import cirucury.core.protocol.basic.{AddressComponent, EnvelopeComponent}
import cirucury.core.protocol.basic.payload.PayloadComponent

trait EnvelopeEncoderComponent[-C,+R] extends EncoderComponent{
  this:EnvelopeComponent with AddressComponent=>
  def EnvelopeEncoder(envelope: Envelope[_<:AddressBase,C]):EncodeResult[R]
}

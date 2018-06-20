package cirucury.core.protocol.basic.extractor

import cirucury.core.protocol.basic.{AddressComponent, EnvelopeComponent}
import cirucury.core.protocol.basic.payload.PayloadComponent

trait EnvelopeExtractorComponent[-S,+T] extends ExtractorComponent{
  this:EnvelopeComponent with AddressComponent=>
  val EnvelopeExtractor:Extractor[S,Envelope[_<:AddressBase,T]]
}

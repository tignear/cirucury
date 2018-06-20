package cirucury.core.protocol.kademlia.io.extractor

import cirucury.core.protocol.basic.extractor.ExtractorComponent
import cirucury.core.protocol.basic.payload.PayloadIdComponent

trait PayloadIdExtractorComponent[-S] extends ExtractorComponent{
  this:PayloadIdComponent=>
  val PayloadIdExtractor:Extractor[S,PayloadId]
}

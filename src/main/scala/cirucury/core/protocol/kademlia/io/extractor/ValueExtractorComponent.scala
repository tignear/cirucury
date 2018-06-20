package cirucury.core.protocol.kademlia.io.extractor

import cirucury.core.protocol.basic.extractor.ExtractorComponent
import cirucury.core.protocol.kademlia.KPayloads.KHaveValuePayloadComponent

trait ValueExtractorComponent[-S] extends ExtractorComponent{
  this:KHaveValuePayloadComponent=>
  val ValueExtractor:Extractor[S,ValueR]
}

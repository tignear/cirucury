package cirucury.core.protocol.kademlia.io.extractor

import cirucury.core.protocol.basic.extractor.ExtractorComponent
import cirucury.dht.kademlia.KNodeInfoComponent

trait KNodeInfoExtractorComponent[-S] extends ExtractorComponent{
  this:KNodeInfoComponent=>
  val KNodeInfoExtractor:Extractor[S,KNodeInfo]
}

package cirucury.core.protocol.kademlia.io.extractor

import cirucury.core.protocol.basic.extractor.ExtractorComponent
import cirucury.core.protocol.kademlia.IdentifierComponent

trait IdentifierExtractorComponent[-S] extends ExtractorComponent{
  this:IdentifierComponent=>
  val IdentifierExtractor:Extractor[S,Identifier]
}

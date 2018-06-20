package cirucury.core.protocol.kademlia.io.extractor

import cirucury.core.protocol.basic.extractor.ExtractorComponent
import cirucury.core.protocol.kademlia.MessageIdComponent

trait MessageIdExtractorComponent[-S] extends ExtractorComponent{
  this:MessageIdComponent=>
  val MessageIdExtractor:Extractor[S,MessageId]
}

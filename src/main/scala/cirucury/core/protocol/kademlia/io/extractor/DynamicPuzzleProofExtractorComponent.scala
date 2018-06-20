package cirucury.core.protocol.kademlia.io.extractor

import cirucury.core.protocol.basic.extractor.ExtractorComponent

trait DynamicPuzzleProofExtractorComponent[-S] extends ExtractorComponent{
  val DynamicPuzzleProofExtractor:Extractor[S,BigInt]
}

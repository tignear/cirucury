package cirucury.core.protocol.kademlia.io.extractor

import java.security.PublicKey

import cirucury.core.protocol.basic.extractor.ExtractorComponent

trait PublicKeyExtractorComponent[-S] extends ExtractorComponent{
  val PublicKeyExtractor:Extractor[S,PublicKey]
}

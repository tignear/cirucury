package cirucury.core.protocol.kademlia.io.extractor

import cirucury.core.protocol.basic.extractor.ExtractorComponent
import cirucury.core.protocol.kademlia.KMessage.{KMessageComponent, KPayloadMessageComponent}
import cirucury.core.protocol.kademlia.KMessage.wrap.{KStrongSignMessageComponent, KWeakSignMessageComponent}
import cirucury.core.protocol.kademlia.KPayloads.KPayloadComponent

object KMessageExtractors {
  trait KSingleMessageExtractorComponent[-S] extends  ExtractorComponent{
    this: KPayloadMessageComponent with KPayloadComponent =>
    val KSingleMessageExtractor:Extractor[S,KPayloadMessage[KPayloadLike]]
  }

  trait KWeakSignMessageExtractorComponent[-S] extends ExtractorComponent {
    this: KWeakSignMessageComponent =>
    val KWeakSignMessageExtractor:Extractor[S,KWeakSignMessage[KMessage]]

  }

  trait KStrongSignMessageExtractorComponent[-S] extends ExtractorComponent {
    this: KStrongSignMessageComponent =>
    val KStrongSignMessageExtractor:Extractor[S,KStrongSignMessage[KMessage]]
  }

  trait KMessageWholeExtractorComponent[-S] extends ExtractorComponent {
    this: KMessageComponent
      with KSingleMessageExtractorComponent[S]
      with KWeakSignMessageExtractorComponent[S]
      with KStrongSignMessageExtractorComponent[S] =>
    val KMessageWholeExtractor:Extractor[S,KMessage]
  }
}

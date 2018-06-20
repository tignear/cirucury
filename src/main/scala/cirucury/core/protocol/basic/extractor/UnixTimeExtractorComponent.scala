package cirucury.core.protocol.basic.extractor

trait UnixTimeExtractorComponent[-S] extends ExtractorComponent{
  val UnixTimeExtractor:Extractor[S,Long]
}

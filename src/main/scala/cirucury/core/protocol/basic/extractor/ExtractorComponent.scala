package cirucury.core.protocol.basic.extractor

trait ExtractorComponent {
  trait Extractor[-S,+R]{
    def unapply(arg:S): Option[R]
  }
}

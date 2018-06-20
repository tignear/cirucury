package cirucury.core.protocol.basic.encoder

trait EncoderComponent{
  trait EncodeResult[+R]{
    def bytes:R
  }
}

package cirucury.core.protocol.basic.encoder

trait UnixTimeEncoderComponent[+R] extends EncoderComponent{
  def UnixTimeEncode(time:Long):EncodeResult[R]
}

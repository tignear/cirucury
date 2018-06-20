package cirucury.core.protocol.kademlia.io.encoder

import cirucury.core.protocol.basic.encoder.EncoderComponent

trait DynamicPuzzleProofEncoderComponent[+R] extends EncoderComponent{
  def DynamicPuzzleProofEncode(proof:BigInt):EncodeResult[R]
}

package cirucury.blockchain.structure

class SHA3_256Hash(val hash:Vector[Byte]){
  if(hash.length!=256)throw new IllegalArgumentException("bad length")
}

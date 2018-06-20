package cirucury.tree.immutable.merkle

object MerklePath {
  def verify(hashFn:(Seq[Byte],Seq[Byte])=>Seq[Byte],path:Seq[Seq[Byte]],rootHash: Seq[Byte],targetHash: Seq[Byte]): Boolean ={
    if(path.isEmpty) rootHash.equals(targetHash)
    verify(hashFn,path.tail,rootHash,hashFn(path.last,targetHash))
  }
}

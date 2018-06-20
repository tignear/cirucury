package cirucury.tree.immutable.merkle

object MerkleTree {
  def apply(hashFn:(Seq[Byte],Seq[Byte])=>Seq[Byte],merkleEnds: MerkleEnd[_] *):MerkleNode={
    if(merkleEnds.length<2) throw new IllegalArgumentException
    if(merkleEnds.length==2){
      new MerkleLeaf(hashFn)(merkleEnds.head,merkleEnds.last)
    }else{
      new MerkleLeaf(hashFn)(apply(hashFn,merkleEnds.slice(0,merkleEnds.length/2): _*),apply(hashFn,merkleEnds.slice(merkleEnds.length/2,merkleEnds.length): _*))
    }
  }
  def calcRoot(hashFn:(Seq[Byte],Seq[Byte])=>Seq[Byte],merkleEnds: Seq[Byte] *):Seq[Byte]={
    if(merkleEnds.length<2)throw new IllegalArgumentException
    if(merkleEnds.length==2){
      hashFn(merkleEnds.head,merkleEnds.last)
    }else{
      hashFn(calcRoot(hashFn,merkleEnds.slice(0,merkleEnds.length/2):_*),calcRoot(hashFn,merkleEnds.slice(merkleEnds.length/2,merkleEnds.length):_*))
    }
  }

}

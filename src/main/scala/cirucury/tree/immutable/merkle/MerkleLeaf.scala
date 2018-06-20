package cirucury.tree.immutable.merkle

class MerkleLeaf (hashFn:(Seq[Byte],Seq[Byte])=>Seq[Byte])(val left: MerkleNode,val right: MerkleNode) extends MerkleNode {
  override val hash = hashFn(left.hash,right.hash)
}

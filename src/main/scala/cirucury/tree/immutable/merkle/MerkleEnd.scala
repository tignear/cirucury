package cirucury.tree.immutable.merkle

class MerkleEnd[A] (hashFn:A=>Seq[Byte])(val value:A) extends MerkleNode {
  override val hash = hashFn(value)
}

package cirucury.tree.immutable.merkle_patricia

import MPTree.MerklePatricia
import cirucury.tree.immutable.patricia.PatriciaHex

abstract class MPHex[A<:MerklePatricia[A]](children:IndexedSeq[Option[A]],value:Option[_]) extends PatriciaHex[A](children,value) with MerklePatricia[A] {
}

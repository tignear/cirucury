package cirucury.tree.immutable.merkle_patricia

import MPTree.MerklePatricia
import cirucury.tree.immutable.patricia.PatriciaEnd

abstract class MPEnd[A<:MerklePatricia[A]](value:Option[_]) extends PatriciaEnd[A](value) with MerklePatricia[A]{
}

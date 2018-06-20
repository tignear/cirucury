package cirucury.tree.immutable.merkle_patricia

import cirucury.tree.immutable.patricia.{PatriciaKV, PatriciaPath}
import cirucury.tree.immutable.merkle_patricia.MPTree.MerklePatricia
import cirucury.tree.immutable.patricia.{PatriciaKV, PatriciaPath}

abstract class MPKV[A<:MerklePatricia[A]](path:PatriciaPath,value:A) extends PatriciaKV[A](path,value) with MerklePatricia[A]{

}

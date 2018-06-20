package cirucury.tree.immutable.patricia

import PatriciaTree.Patricia

trait PatriciaValueNode[A<:Patricia[A]]  extends Patricia[A]{
  val value:Option[Any]
}

package cirucury.tree.immutable.patricia

import PatriciaTree.Patricia

abstract class PatriciaEnd[A<:Patricia[A]] protected(override val value: Option[_]) extends PatriciaValueNode[A] {

  override def update(path: PatriciaPath, v: Option[_]):A =
    if(path.isEmpty){
      factory.createEnd(v)
    }else{
      factory.createHex(Map(path.head.toInt->factory.createKV(path.tail,factory.createEnd(value))),v)
    }

  override def apply(path: PatriciaPath) = if(path.isEmpty) value else throw new IllegalArgumentException

  override def isEmpty = value.isEmpty
}
/*object PatriciaEnd{
  def apply[A<:PatriciaNode[A]](v:Option[_]):PatriciaEnd[A]=new PatriciaEnd(v)[A]
}*/

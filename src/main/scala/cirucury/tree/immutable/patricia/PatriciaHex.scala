package cirucury.tree.immutable.patricia

import PatriciaTree.Patricia

abstract class PatriciaHex[A<:Patricia[A]](val children:IndexedSeq[Option[A]], override val value: Option[Any]) extends/* PatriciaBase with*/ PatriciaValueNode[A] {
  if(children.isEmpty) throw new IllegalArgumentException
  override def update(path: PatriciaPath, v: Option[_]):A={
    if(path.isEmpty) factory.createHex(children,v)
    else {
      factory.createHex(children.updated(path.head,children(path.head).map(e=>update(path.tail,v)).orElse(Some(factory.createKV(path.tail,factory.createEnd(v))))),value)
    }
  }

  override def apply(path: PatriciaPath) =if(path.isEmpty) value else children(path.head).map(_.apply(path.tail))

  override def isEmpty =children.forall(e=>e.isEmpty||e.get.isEmpty)

}
/*object PatriciaHex{
  /**
    *
    * @param buf key must 0 until 16
    * @param value default is Option.empty
    * @return
    */
  def apply[A<:PatriciaNode[A]](buf:Map[Int,PatriciaNode[_]], value:Option[Any]=Option.empty):PatriciaNode[A] ={
    val buf2=buf.mapValues(Option(_))
    new PatriciaHex(for (i<-0 until 16)yield buf2.getOrElse(i,Option.empty),value)[A]
  }
  def apply[A<:PatriciaNode[A]](vector: IndexedSeq[Option[PatriciaNode[_]]],value:Option[_]=Option.empty):PatriciaHex[A]=new PatriciaHex(vector,value)[A]
}*/

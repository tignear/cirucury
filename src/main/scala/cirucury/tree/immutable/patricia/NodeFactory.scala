package cirucury.tree.immutable.patricia

import PatriciaTree.Patricia

trait NodeFactory[A<:Patricia[A]]{
  def createEnd(value:Option[_]):A
  def createEnd(value:Any):PatriciaNode=createEnd(Option(value))
  def createHex(m:Map[Int,A],value:Option[_]):A={
    val buf2=m.mapValues(Option(_))
    createHex(for (i<-0 until 16)yield buf2.getOrElse(i,Option.empty),value)
  }
  def createHex(m:IndexedSeq[Option[A]],value:Option[_]):A
  def createKV(path:PatriciaPath,value:A): A
}

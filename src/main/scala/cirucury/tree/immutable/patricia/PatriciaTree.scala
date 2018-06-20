package cirucury.tree.immutable.patricia

object PatriciaTree extends NodeFactory[PatriciaNode]{

  trait Patricia[A<:Patricia[A]]{
    def update(path:PatriciaPath,value:Option[_]):A
    def apply(path: PatriciaPath):Option[_]
    def isEmpty:Boolean
    def factory:NodeFactory[A]
  }

  private class KVImpl(key:PatriciaPath,value:PatriciaNode) extends PatriciaKV[PatriciaNode](key,value) with PatriciaNode{}
  private class HexImpl(children:IndexedSeq[Option[PatriciaNode]], value:Option[_])extends PatriciaHex[PatriciaNode](children,value)  with PatriciaNode{}
  private class EndImpl(value:Option[_]) extends PatriciaEnd[PatriciaNode](value) with PatriciaNode{}

  override def createEnd(value:Option[_]):PatriciaNode=new EndImpl(value)
  override def createHex(m: IndexedSeq[Option[PatriciaNode]], value: Option[_]):PatriciaNode = new HexImpl(m,value)
  override def createKV(path: PatriciaPath, value: PatriciaNode):PatriciaNode = new KVImpl(path,value)
}

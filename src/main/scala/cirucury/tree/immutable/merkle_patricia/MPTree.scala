package cirucury.tree.immutable.merkle_patricia


import cirucury.tree.immutable.patricia.{NodeFactory, PatriciaPath}
import cirucury.tree.immutable.patricia.PatriciaTree.Patricia

object MPTree {
  trait MerklePatricia[A<:MerklePatricia[A]] extends Patricia[A]{
    val hash:Seq[Byte]
  }

  type endHash=Option[_]=>Seq[Byte]
  type hexHash=(Seq[Seq[Byte]],Option[_])=>Seq[Byte]
  type kvHash=(PatriciaPath,Seq[Byte])=>Seq[Byte]

  private class EndImpl(value:Option[_],hashFn:endHash,val factory: NodeFactory[MPNode])extends MPEnd[MPNode](value) with MPNode{
    override val hash = hashFn(value)
  }
  private class HexImpl(children:IndexedSeq[Option[MPNode]],value:Option[_],hashFn:hexHash,val factory: NodeFactory[MPNode]) extends MPHex[MPNode](children,value) with MPNode{
    override val hash = hashFn(children.map(_.map(_.hash).getOrElse(Seq.empty)),value)
  }
  private class KVImpl(path:PatriciaPath,value:MPNode,hashFn:kvHash,val factory: NodeFactory[MPNode]) extends MPKV[MPNode](path,value) with MPNode{
    override val hash = hashFn(path,value.hash)
  }
  class MPFactory(endHash:endHash,hexHash:hexHash,kvHash:kvHash) extends NodeFactory [MPNode]{
    override def createEnd(value: Option[_]):MPNode = new EndImpl(value,endHash,this)

    override def createHex(m: IndexedSeq[Option[MPNode]], value: Option[_]):MPNode = new HexImpl(m,value,hexHash,this)

    override def createKV(path: PatriciaPath, value: MPNode):MPNode = new KVImpl(path,value,kvHash,this)
  }
}

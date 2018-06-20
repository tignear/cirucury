package cirucury.tree.immutable.patricia

import PatriciaTree.Patricia

abstract class PatriciaKV[A<:Patricia[A]] protected(val key:PatriciaPath, val value:A) extends Patricia[A]{
  if(value.isInstanceOf[PatriciaKV[A]])throw new IllegalArgumentException

  private def createHelper(t2:(PatriciaPath,A) *):A= {
    val v=t2.find{case(p,_)=>p.isEmpty}
    val e=t2.filterNot{case(p,_)=>isEmpty}.map{case(p,v)=>(p.head.toInt,factory.createKV(p.tail,v))}
    factory.createHex(e.toMap,v)
  }
  override def update(k: PatriciaPath, v: Option[_]):A = {
    if(k.contain(key) eq key){
      factory.createKV(this.key,this.value.update(k.slice(key),v))
    }else{
      val contain=k.contain(key)
      factory.createKV(contain, createHelper((key.slice(contain), value), (k.slice(contain), factory.createEnd(v))))
    }
  }

  override def apply(path: PatriciaPath) = value(path.slice(key))

  override def isEmpty = value.isEmpty
}
/*object PatriciaKV{
  def apply[A<:PatriciaNode[A]](ke:PatriciaPath,node:A):A  =node match {
    case e if ke.isEmpty=>e
    case kv:PatriciaKV[A]=>new PatriciaKV(ke++kv.key,kv.value)
    case e=>new PatriciaKV(ke,e)
  }
}*/

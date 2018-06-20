package cirucury.tree.immutable.patricia

import PatriciaPath.ofHex

import scala.collection.immutable.{LinearSeq, Nil}
import scala.language.postfixOps

final class PatriciaPath (val raw:LinearSeq[Byte], val isOdd:Boolean){
  override def equals(obj: scala.Any):Boolean = obj match {
    case v:PatriciaPath=>isOdd==v.isOdd&&raw.equals(v.raw)
    case _=>false
  }
  def hexValue:LinearSeq[Byte]=hexValueHelper(raw,isOdd)
  private def hexValueHelper(raw:LinearSeq[Byte],isOdd:Boolean): LinearSeq[Byte] ={
    val r=raw.flatMap(e => (e << 4).toByte :: (e & 0x0f).toByte ::Nil)
    if(isOdd) r.tail else r
  }
  def head=hexValue.head
  def tail:PatriciaPath=if(isOdd) new PatriciaPath(raw.tail,false) else new PatriciaPath(raw.updated(0,hexValue.head),true)
  def slice(s:Int,until:Int):PatriciaPath={
    if(s%2==0){
      if(until%2==0){
        new PatriciaPath(raw.slice(s/2,until/2),false)
      }else{
        ofHex(hexValueHelper(raw.slice(s/2,until/2+1),isOdd).init)
      }
    }else{
      if(until%2==0){
        ofHex(hexValueHelper(raw.slice(s/2,until/2),isOdd).tail)
      }else{
        ofHex(hexValueHelper(raw.slice(s/2,until/2+1),isOdd).tail.init)
      }
    }
  }
  def slice(that: PatriciaPath):PatriciaPath=ofHex(hexValue diff that.hexValue)
  def isEmpty=raw.isEmpty
  def length=if(isOdd)raw.length*2-1 else raw.length*2
  def ++(path:PatriciaPath)=ofHex(path.hexValue++path.hexValue)
  def contain(that:PatriciaPath):PatriciaPath=ofHex((hexValue zip that.hexValue).takeWhile{case(a,b)=>a==b}.map{case(a,_)=>a})

  override def hashCode() = (isOdd##)*31+(raw##)

  override def toString = s"PatriciaPath($raw, $isOdd)"
}
object PatriciaPath {
  def ofHex(hex:LinearSeq[Byte]):PatriciaPath={
    if(hex.length%2==0){
      new PatriciaPath(hex.zipWithIndex.partition{case(_,i)=>i%2==0}.zipped.map{case((a,_),(b,_))=>((a<<4)|(b&0xf)).toByte},false)
    }else{
      new PatriciaPath((hex.head&0xf).toByte+:hex.tail.zipWithIndex.partition{case(_,i)=>i%2!=0}.zipped.map{case((a,_),(b,_))=>((a<<4)|(b&0xf)).toByte},true)
    }
  }
  val empty=new PatriciaPath(LinearSeq.empty,true)
}
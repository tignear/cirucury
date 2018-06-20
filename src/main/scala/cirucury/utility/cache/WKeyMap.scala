package cirucury.utility.cache

import scala.collection.mutable

class WKeyMap[K1,K2,V](sup:((()=>mutable.Map[K1,(K2,V)],()=>mutable.Map[K2,(K1,V)]))=(()=>new mutable.HashMap[K1,(K2,V)](),()=>new mutable.HashMap[K2,(K1,V)]())){
  private val k1m=sup._1()
  private val k2m=sup._2()
  def apply1(k:K1):V=k1m(k)._2
  def apply2(k:K2):V=k2m(k)._2
  def get1(k:K1)=k1m.get(k).map(_._2)
  def get2(k:K2)=k2m.get(k).map(_._2)
  def put(k1:K1,k2: K2,v: V):Unit ={
    k1m.put(k1,(k2,v))
    k2m.put(k2,(k1,v))
  }
  def remove1(k1: K1): Option[V] ={
    k1m.remove(k1) match {
      case Some(v)=>k2m.remove(v._1).map{case(_,e)=>e}
      case None=>None
    }
  }
  def remove2(k2: K2):Option[V]={
    k2m.remove(k2) match{
      case Some(v)=>k1m.remove(v._1).map{case(_,e)=>e}
      case None=>None
    }
  }
  def size=k1m.size
}

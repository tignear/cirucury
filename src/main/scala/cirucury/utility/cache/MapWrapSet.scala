package cirucury.utility.cache

import scala.collection.mutable

class MapWrapSet[V](map:mutable.Map[V,AnyRef]) extends mutable.Set[V]{
  override def +=(elem: V) = {map+=(elem->AnyRef);this}

  override def -=(elem: V) = {map-=elem;this}

  override def contains(elem: V) = map.keySet.contains(elem)

  override def iterator =map.keysIterator
}
object MapWrapSet{
  def apply[V](map: mutable.Map[V, AnyRef]): MapWrapSet[V] = new MapWrapSet[V](map)
}
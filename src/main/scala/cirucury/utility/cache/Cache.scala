package cirucury.utility.cache

import scala.collection.mutable

object Cache {
  def apply[A,B](maxSize:Int): mutable.LinkedHashMap[A,B] = new CacheImpl(maxSize)
  private class CacheImpl[A, B](maxSize:Int) extends mutable.LinkedHashMap[A,B]{
    override def put(key: A, value: B) ={
      if (maxSize<=size) this.tail
      super.put(key, value)
    }

  }
}

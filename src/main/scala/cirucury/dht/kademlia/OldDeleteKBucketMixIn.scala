package cirucury.dht.kademlia

import scala.collection.mutable

trait OldDeleteKBucketMixIn extends KBucketComponent{
  this:KNodeInfoComponent=>
  class OldDeleteKBucket(val maxSize: Int) extends KBucketLike {
    private val lbuf: mutable.LinkedHashSet[KNodeInfo] = mutable.LinkedHashSet()

    override def add(v: KNodeInfo): Unit = {
      if (maxSize <= nowSize()) lbuf -= lbuf.head
      lbuf += v
    }

    override def nowSize() = lbuf.size

    override def iterator = lbuf.iterator

  }

  override type KBucket = OldDeleteKBucket

  override def KBucket(maxSize: Int) = new OldDeleteKBucket(maxSize)
}

package cirucury.dht.kademlia

trait KBucketComponent {
  this:KNodeInfoComponent=>
  type KBucket<:KBucketLike
  trait KBucketLike extends Iterable[KNodeInfo] {
    this:KBucket=>
    def add(v: KNodeInfo)

    val maxSize: Int

    def nowSize(): Int

    def +=(that: KNodeInfo): Unit = add(that)
  }
  def KBucket(maxSize:Int):KBucket
}

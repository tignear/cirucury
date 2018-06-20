package cirucury.dht.kademlia

trait BasicKRootingTableMixIn extends KRootingTableComponent{
  this:KIdComponent with KBucketComponent with KNodeInfoComponent=>
  class BasicKRootingTable(val myId: KId, private val supply: () => KBucket) extends KRootingTableLike {
    private def buildBuckets(idLengthBit: Int, supply: () => KBucket): Vector[KBucket] = {
      val vb = Vector.newBuilder[KBucket]
      vb.sizeHint(idLengthBit + 1)
      for (_ <- 0 to idLengthBit) {
        vb += supply.apply()
      }
      vb.result()
    }

    private val buckets: Vector[KBucket] = buildBuckets(myId.lengthBit, supply)

    override def update(kNodeInfo: KNodeInfo): Unit = {
      def getRootingTableIndex(v: KId): Int = {
        // xor距離を計算する
        val distance = v.distance(myId)
        // 対応するkBucketを探す。
        distance.bits.zipWithIndex.find { case (e, _) => e }.head._2
      }

      val index = getRootingTableIndex(kNodeInfo.peerId)
      buckets(index) += kNodeInfo
    }

    override def find(target: KId): Vector[KNodeInfo] = buckets.flatMap(_.iterator).map(e => (e, e.peerId)).sorted[(KNodeInfo, KId)] {
      case ((_, ai), (_, bi)) => KIdCompanion.distanceSorter(target).compare(ai, bi)
    }.map(_._1)

  }

  override type KRootingTable = BasicKRootingTable

  override def KRootingTable(myId: KId) = new BasicKRootingTable(myId,()=>KBucket(20))
}
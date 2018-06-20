package cirucury.dht.kademlia

trait KRootingTableComponent {
  this:KIdComponent with KNodeInfoComponent=>
  trait KRootingTableLike {
    def update(kid:KNodeInfo )

    def find(target: KId): Seq[KNodeInfo]
  }
  type KRootingTable<:KRootingTableLike
  def KRootingTable(myId: KId):KRootingTable
}

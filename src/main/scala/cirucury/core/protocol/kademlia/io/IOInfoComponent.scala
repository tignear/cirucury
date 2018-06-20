package cirucury.core.protocol.kademlia.io

object IOInfoComponent {
  trait KNodesResponseInfoComponent{
    trait KNodesResponseInfoBase{
      val BYTES:Int
      val BITS:Int
    }
    type KNodesResponseInfo<:KNodesResponseInfoBase
    val KNodesResponseInfo:KNodesResponseInfo
  }
}

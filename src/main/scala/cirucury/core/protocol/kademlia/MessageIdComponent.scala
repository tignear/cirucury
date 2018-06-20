package cirucury.core.protocol.kademlia

trait MessageIdComponent {
  type MessageId
  trait MessageIdCompanionBase{
    val BYTES:Int
    val BITS:Int
  }
  type MessageIdCompanion<:MessageIdCompanionBase
  val MessageIdCompanion:MessageIdCompanion
}

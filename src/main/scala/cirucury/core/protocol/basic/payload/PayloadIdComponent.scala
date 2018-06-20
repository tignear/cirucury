package cirucury.core.protocol.basic.payload

trait PayloadIdComponent {
  type PayloadId
  trait PayloadIdCompanionBase{
    val BYTES:Int
    val BITS:Int
  }
  type PayloadIDCompanion<:PayloadIdCompanionBase
  val PayloadIdCompanion:PayloadIDCompanion
}

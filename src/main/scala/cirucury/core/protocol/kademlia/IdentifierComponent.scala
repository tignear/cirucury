package cirucury.core.protocol.kademlia

trait IdentifierComponent {
  type Identifier
  trait IdentifierCompanionBase{
    val BYTES:Int
    val BITS:Int
  }
  type IdentifierCompanion<:IdentifierCompanionBase
  val IdentifierCompanion:IdentifierCompanion
}

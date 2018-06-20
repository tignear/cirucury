package cirucury.client.protocol.impl.kademlia.payload

import cirucury.core.protocol.kademlia.IdentifierComponent

trait IntIdentifierMixIn extends IdentifierComponent{
  override type Identifier =Int
  override type IdentifierCompanion = IdentifierCompanionBase
  override val IdentifierCompanion = new IdentifierCompanionBase {
    override val BYTES = 4
    override val BITS = BYTES*8
  }
}

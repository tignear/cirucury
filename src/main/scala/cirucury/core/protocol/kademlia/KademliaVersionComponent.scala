package cirucury.core.protocol.kademlia

import cirucury.core.protocol.basic.IntVersionMixIn

trait KademliaVersionComponent extends IntVersionMixIn{
  val KademliaVersion:Version
}

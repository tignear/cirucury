package cirucury.core.protocol.kademlia.io.encoder

import cirucury.core.protocol.basic.encoder.EncoderComponent
import cirucury.dht.kademlia.KNodeInfoComponent
import scala.language.implicitConversions

trait KNodeInfoEncoderComponent[+R] extends EncoderComponent{
  this:KNodeInfoComponent=>
  implicit def KNodeInfoEncode(kNodeInfo:KNodeInfo):EncodeResult[R]
}

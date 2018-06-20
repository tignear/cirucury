package cirucury.dht
trait DHTIdComponent {
  trait DHTId[T <: DHTId[T]] extends Ordered[T] {
    def distance(other: T): T

    def apply(index: Int): Byte

    val lengthBit: Int
    val lengthByte: Int
  }
}

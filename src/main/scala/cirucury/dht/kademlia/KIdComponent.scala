package cirucury.dht.kademlia


import cirucury.dht.DHTIdComponent
import cirucury.hash.HashFunction

trait KIdComponent extends DHTIdComponent{
  type KId<:KIdLike
  trait KIdLike extends DHTId[KId] with Iterable[Byte] {
    this:KId=>
    val bytes: Seq[Byte]
    override val lengthByte: Int = KIdCompanion.BYTES
    override val lengthBit: Int = lengthByte * 8

    override def equals(obj: Any): Boolean =obj match {
      case kid: KIdLike if bytes eq kid.bytes => true
      case _ => false
    }
    override def compare(that: KId): Int = {
      for ((a, b) <- bytes zip that.bytes) {
        val c = a.compareTo(b)
        if (c != 0) {
          return c
        }
      }
      0
    }

    override def distance(other: KId):KId = KId(this.bytes zip other.bytes map { case (a, b) => (a ^ b).asInstanceOf[Byte] })

    override def hashCode() = bytes.hashCode

    override def iterator = bytes.iterator

    override def apply(index: Int) = bytes(index)

    def bits: Seq[Boolean] = {
      def toBit(byte: Byte)(bit: Int) = ((byte >> bit) & 1) == 1

      def byte2Bools(b: Byte): Seq[Boolean] = (0 to 7).foldLeft(Seq[Boolean]()) { (bs, i) => bs :+ toBit(b)(i) }

      bytes.flatMap(byte2Bools)
    }
  }
  type KIdCompanion<:KIdCompanionBase
  trait KIdCompanionBase{
    val BYTES:Int
    val BITS:Int
    val hashFunction:HashFunction[Seq[Byte],Seq[Byte]]
    def distanceSorter(target:KId):Ordering[KId]=(ai,bi)=>ai.distance(target).compare(bi.distance(target))
  }
  val KIdCompanion:KIdCompanion
  def KId(seq:Seq[Byte]):KId

}
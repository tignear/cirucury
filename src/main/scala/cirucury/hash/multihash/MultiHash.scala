package cirucury.hash.multihash

import java.security.MessageDigest

import cirucury.hash.HashFunction

import scala.language.postfixOps


object MultiHash{
  def apply(hashType: HashType)(src:Byte *)=new MultiHashResult(hashType,src:_*)
  class MultiHashResult (val hashType: HashType, val raw:Byte *) {//raw
    val length:Short=raw.length.toShort
    val hash:Seq[Byte]=Seq(hashType.fnCode<<8 toByte,hashType.fnCode.toByte,length<<8 toByte,length.toByte)++raw
  }
  sealed abstract class HashType(val fnCode:Short,val name:String,val messageDigestName:String) extends HashFunction[Array[Byte],MultiHashResult]{
    def this(fnCode: Short,name:String) = this(fnCode, name,name)
    override def toString = name
    def apply(src:Array[Byte]):MultiHashResult=new MultiHashResult(this,hash(src):_*)
    def hash(src:Array[Byte]):Array[Byte]=MessageDigest.getInstance(messageDigestName).digest(src)
  }
  case object Identity extends HashType(0x00,???)

  case object MD4 extends HashType(0xd4,"MD4")

  case object MD5 extends HashType(0xd5,"MD5")

  case object SHA1 extends HashType(0x11,"SHA1")

  case object SHA2_256 extends HashType(0x12,"SHA-256")

  case object SHA2_512 extends HashType(0x13,"SHA-512")

  case object DBL_SHA2_256 extends HashType(0x56,???)

  case object SHA3_224 extends HashType(0x17,"SHA3-224")

  case object SHA3_256 extends HashType(0x16,"SHA3-256")

  case object SHA3_384 extends HashType(0x15,"SHA3-384")

  case object SHA3_512 extends HashType(0x14,"SHA3-512")


}
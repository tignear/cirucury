package cirucury.dht.s_kademlia

import java.security.PublicKey

import cirucury.dht.kademlia.KIdComponent
import scala.language.postfixOps
/**
  * @see http://www.spovnet.de/files/publications/SKademlia2007.pdf
  */
trait NodeIdComponent extends KIdComponent{
  val dynamicDifficulty:Int
  val staticDifficulty:Int
  override type KId = KIdLike
  trait NodeId extends KIdLike{
    this:KId=>
    val publicKey:PublicKeyType
    val x:BigInt//crypto puzzle solved
  }
  type PublicKeyType<:PublicKey

  override def KId(_seq: Seq[Byte]) = new KIdLike {
    override val bytes:Seq[Byte] = _seq
  }

  val verifyStatic=verifyPZero _
  def verifyPZero(double:Seq[Byte],lenBit:Int):Boolean={
    var b=lenBit
    var p=0
    while(b>=8){
      if(double(p)!=0) return false
      b-=8
      p+=1
    }
    (double(p)&(0xff<<(8-b)))==0
  }
  def verifyDynamic(single:Seq[Byte],x:Seq[Byte],lenBit:Int): Boolean =verifyPZero(KIdCompanion.hashFunction(single.zipAll(x,0 toByte,0 toByte).map{case (a,b)=>a^b toByte}),lenBit)
  def NodeId(_publicKey:PublicKeyType,pkBytes:Seq[Byte],idBytes:Seq[Byte],_x:BigInt):Option[NodeId]={
    val single=KIdCompanion.hashFunction(pkBytes)
    if(!(single == idBytes)) return None
    if(!verifyStatic(KIdCompanion.hashFunction(single),4)) return None
    if(!verifyDynamic(single,_x.toByteArray,4))return  None
    Some(new NodeId{
      override val publicKey = _publicKey
      override val bytes = idBytes
      override val x=_x
    })
  }
}

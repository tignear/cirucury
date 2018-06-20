package cirucury.dht.s_kademlia

import java.security.PrivateKey
import scala.language.postfixOps
trait PrivateNodeIdComponent extends NodeIdComponent{
  trait PrivateNodeId  extends NodeId{
    val privateKey:PrivateKeyType
  }
  type PrivateKeyType<:PrivateKey
  def PrivateNodeId(factory:()=>(PublicKeyType,Array[Byte],PrivateKeyType)): Unit ={
    def help(): Option[(PublicKeyType,Array[Byte],PrivateKeyType)] ={
      val obj=factory()
      if(verifyStatic(obj._2,staticDifficulty))Some(obj) else None
    }
    val r=Iterator.continually(help()).find(_.isDefined).flatten.get
    def calcX(pk:Array[Byte],difficulty:Int):BigInt ={
      implicit class xorArray(seq:Array[Byte]){
        def ^(that:Array[Byte]):Array[Byte]=seq.zipAll(that,0 toByte,0 toByte).map{case(a,b)=>a^b toByte}
      }
      while(true){
        var x=BigInt(0)
        if(verifyPZero(pk ^ x.toByteArray,difficulty)) return x
        x+=1
      }
      throw new IllegalStateException()
    }
    val _x=calcX(r._2,dynamicDifficulty)
    new PrivateNodeId {
      override val privateKey =r._3
      override val x = _x
      override val publicKey = r._1
      override val bytes = KIdCompanion.hashFunction(r._2)
    }
  }
}

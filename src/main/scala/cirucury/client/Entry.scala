package cirucury.client

import akka.actor.SupervisorStrategy.{Restart, Resume}
import akka.actor.{Actor, ActorRef, ActorSystem, OneForOneStrategy, Props}
import akka.routing.BalancingPool
import cirucury.client.Entry.App.In


object Entry{
  class Worker extends Actor{
    println(context.toString+":start")
    override def receive = {
      case "Ex"=>throw new IllegalArgumentException
      case a=>
        println(sender())
        sender()!(a+":w")
    }
  }
  class Router extends Actor{
    val nWorkers=3
    val supervision= OneForOneStrategy() {
      case x:Exception=>
        Resume
    }
    val pool=context.actorOf(BalancingPool(nWorkers,supervision).props(Props[Worker]),"AA")
    override def receive = {
      case a=>
        println(a)
        pool.forward(a)
    }
  }
  object App{
    case class In(a:Any)
  }
  class App extends Actor{
    val router=context.actorOf(Props[Router])
    override def receive = {
      case In(a)=>router ! a
      case a=>println("return:"+a)
    }

  }
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("system")
    val app = system.actorOf(Props[App])
    app ! In("ss")
    app ! In("Ex")
    app ! In("s2")
    app ! In("Ex")
    app ! In("Ex")
    app !In("s3")
    Thread.sleep(1000)
    app !In("s4")
    Thread.sleep(5000)

    system.terminate()
    println("exit")
  }
  def sign(args:Array[String]): Unit = {
    import java.security.KeyPairGenerator
    import java.security.SecureRandom
    import java.security.Signature
    /*
    *  楕円曲線暗号 鍵ペア生成
    */
    // 鍵ペア生成器
    val keyGen = KeyPairGenerator.getInstance("EC")
    // Elliptic Curve
    // 乱数生成器
    val randomGen = SecureRandom.getInstance("SHA1PRNG")
    // 鍵サイズと乱数生成器を指定して鍵ペア生成器を初期化
    val keySize = 256
    keyGen.initialize(keySize, randomGen)

    // 鍵ペア生成
    val keyPair = keyGen.generateKeyPair
    // 秘密鍵
    val privateKey = keyPair.getPrivate
    // 公開鍵
    val publicKey = keyPair.getPublic
    /*
     * 署名生成
     */
    val originalText = "This is string to sign"

    // 署名生成アルゴリズムを指定する
    val dsa = Signature.getInstance("SHA1withECDSA")
    // 初期化
    dsa.initSign(privateKey)
    // 署名生成
    dsa.update(originalText.getBytes("UTF-8"))
    // 生成した署名を取り出す
    val signature = dsa.sign
    System.out.println("Signature: " +signature)

    /*
     * 署名検証
     */

    dsa.initVerify(publicKey)
    // 署名検証する対象をセットする
    dsa.update(originalText.getBytes("UTF-8"))
    // 署名検証
    val verifyResult = dsa.verify(signature)
    System.out.println("Verify: " + verifyResult)
  }
}

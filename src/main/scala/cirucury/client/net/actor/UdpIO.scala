package cirucury.client.net.actor

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef}
import akka.event.Logging
import akka.io.{IO, Udp}
import cirucury.client.net.actor.UdpIO.SubscribeRecvHandle

class UdpIO(bind:InetSocketAddress)  extends Actor{
  import context.system
  val log = Logging(context.system, this)
  IO(Udp) ! Udp.Bind(self, bind)
  var queue:Option[ActorRef]=None
  override def receive = {
    case Udp.Bound(local) =>
      context.become(ready(sender()))
      log.info(s"Started udpIO:$local")
    case SubscribeRecvHandle(q)=>if(queue.isEmpty)this.queue=Option(q) else sender()!new IllegalStateException
  }

  def ready(socket: ActorRef): Receive = {
    case send:Udp.Send=>socket ! send
    case recv:Udp.Event=>queue.foreach(_!recv)
    case SubscribeRecvHandle(q)=>if(queue.isEmpty) this.queue=Option(q) else sender()!new IllegalStateException
  }
}
object UdpIO{
  case class SubscribeRecvHandle(ref:ActorRef)
}
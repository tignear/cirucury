package cirucury

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ClosedShape, Inlet, Outlet}
import akka.stream.scaladsl.{Balance, Flow, GraphDSL, RunnableGraph, Sink, Source, UnzipWith}
import akka.util.ByteString

object Free {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("system")
    implicit val materializer=ActorMaterializer()
    val s=Source(0 to 50)
      val f=Flow.fromFunction[Int,String](e=>if (e % 2 ==0) "A" else "B")
    val unzip=UnzipWith[String,ByteString,String]({
      case "A"=>(ByteString("1"),null)
      case "B"=>(null,"2")
    })
    import GraphDSL.Implicits._
    RunnableGraph.fromGraph(GraphDSL.create() { implicit builder =>
      val S: Outlet[Int]                  = builder.add(s).out
      val F=builder.add(f)
      val U=builder.add(unzip)
      val G: Inlet[Any]                   = builder.add(Sink.foreach[Any](e=>s"${e}:1")).in
      val G2: Inlet[Any]                   = builder.add(Sink.foreach[Any](e=>s"${e}:2")).in
      S ~> F ~> U.in
      U.out0 ~> G
      U.out1 ~> G2
      ClosedShape
    }).run()
  }
}

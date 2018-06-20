package cirucury.tree.mutable.simple

import scala.collection.mutable

class Leaf[A](var value:Option[A],val children :mutable.ArrayBuffer[Node[A]]= mutable.ArrayBuffer[Node[A]]()) extends Node[A] {
}
object Leaf{
  def apply[A](value:Option[A],children:A*):Leaf[A]= new Leaf[A](value,mutable.ArrayBuffer(children:_*).map(e=>new Leaf(Some(e))))

}
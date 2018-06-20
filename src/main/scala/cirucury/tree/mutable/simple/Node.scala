package cirucury.tree.mutable.simple

import scala.collection.mutable

trait Node [A]{
  var value:Option[A]
  val children:mutable.ArrayBuffer[Node[A]]
  def apply(i:Int)=children(i)
  def apply=value.get

  def apply(a:A):Unit =children+=new Leaf[A](Some(a))
}

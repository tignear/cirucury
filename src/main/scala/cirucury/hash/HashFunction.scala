package cirucury.hash

trait HashFunction [-Input,+Output]{
  val name:String
  def apply(in:Input):Output
}
object HashFunction{

}
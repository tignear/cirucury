package cirucury.utility

object MChain {
  implicit class MC[T](x:T){
    def andAction (act:T=>Unit):T={act(x);x}
    def andThen[U](fn:T=>U):U=fn(x)
  }
}

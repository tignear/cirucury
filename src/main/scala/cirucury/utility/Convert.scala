package cirucury.utility

import scala.collection.generic.CanBuildFrom

/**
  * c# like
  */
object Convert {
  def bigEndian[S](in:Int)(implicit cbf:CanBuildFrom[_<:Any,Byte,S]):S=(cbf()+=(in>>>24).toByte+=(in>>16).toByte+=(in>>8).toByte+=in.toByte).result()
  def littleEndian[S](in:Int)(implicit cbf:CanBuildFrom[_<:Any,Byte,S]):S=(cbf()+=in.toByte+=(in>>8).toByte+=(in>>16).toByte+=(in>>>24).toByte).result()
}

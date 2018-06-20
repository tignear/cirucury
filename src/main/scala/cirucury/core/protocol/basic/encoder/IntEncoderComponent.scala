package cirucury.core.protocol.basic.encoder

import cirucury.utility.Convert

import scala.collection.generic.CanBuildFrom
import scala.language.implicitConversions

trait IntEncoderComponent[+R] extends EncoderComponent{
  implicit def IntEncoder(f:Int):EncodeResult[R]
}

object IntEncoderComponent{
  def BigEndian[R](implicit cbf:CanBuildFrom[_<:Any,Byte,R]):IntEncoderComponent[R]=new IntEncoderComponent[R]{
    override def IntEncoder(f: Int) = new EncodeResult[R] {
      override val bytes = Convert.bigEndian(f)
    }
  }
  def LittleEndian[R](implicit cbf:CanBuildFrom[_<:Any,Byte,R]):IntEncoderComponent[R]=new IntEncoderComponent[R]{
    override def IntEncoder(f: Int) = new EncodeResult[R] {
      override val bytes = Convert.littleEndian(f)
    }
  }
}
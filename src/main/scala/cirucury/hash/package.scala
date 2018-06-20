package cirucury

import java.security.MessageDigest

package object hash {
  def double(md:MessageDigest):Array[Byte]=>Array[Byte]=bb=>md.digest(md.digest(bb))
}

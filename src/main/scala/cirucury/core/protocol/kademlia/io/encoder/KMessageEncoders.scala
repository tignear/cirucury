package cirucury.core.protocol.kademlia.io.encoder

import cirucury.core.protocol.basic.encoder.EncoderComponent
import cirucury.core.protocol.kademlia.KMessage.wrap.{KStrongSignMessageComponent, KWeakSignMessageComponent}
import cirucury.core.protocol.kademlia.KMessage.{KMessageComponent, KPayloadMessageComponent}
import cirucury.core.protocol.kademlia.KPayloads.KPayloadComponent

object KMessageEncoders {

  trait KSingleMessageEncoderComponent[+R] extends EncoderComponent {
    this: KPayloadMessageComponent with KPayloadComponent =>
    implicit def KSingleMessageEncode(kademliaSingleMessage: KPayloadMessage[_ <: KPayloadLike]): EncodeResult[R]
  }

  trait KWeakSignMessageEncoderComponent[+R] extends EncoderComponent {
    this: KWeakSignMessageComponent =>
    implicit def WeakSignMessageEncode(weakSignMessage: KWeakSignMessage[_ <: KMessage]): EncodeResult[R]
  }

  trait StrongSignMessageEncoderComponent[+R] extends EncoderComponent {
    this: KStrongSignMessageComponent =>
    implicit def StrongSignMessageEncode(strongSignMessage: KStrongSignMessage[_ <: KMessage]): EncodeResult[R]
  }

  trait MessageWholeEncoderComponent[+R] extends EncoderComponent {
    this: KMessageComponent
      with KSingleMessageEncoderComponent[R]
      with KWeakSignMessageEncoderComponent[R]
      with StrongSignMessageEncoderComponent[R] =>
    implicit def MessageWholeEncode(m: KMessage): EncodeResult[R]
  }
}

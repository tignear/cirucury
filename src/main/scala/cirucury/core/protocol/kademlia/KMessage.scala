package cirucury.core.protocol.kademlia

import java.security.PublicKey
import KPayloads.KPayloadComponent
import cirucury.core.protocol.basic.AddressComponent
import cirucury.dht.s_kademlia.PrivateNodeIdComponent

object KMessage {

  trait KMessageComponent {
    this: MessageIdComponent =>

    sealed trait KMessage {
      val messageId: MessageId
    }

  }

  trait KPayloadMessageComponent extends KMessageComponent {
    this: KPayloadComponent with IdentifierComponent with MessageIdComponent =>

    trait KPayloadMessage[+Payload <: KPayloadLike] extends KMessage {
      val identifier: Identifier
      val payload: Payload
      final override val messageId = KPayloadSingleMessageId
    }

    def KPayloadMessage[Payload <: KPayloadLike](identifier: Identifier, payload: Payload): KPayloadMessage[Payload]

    val KPayloadSingleMessageId: MessageId
  }

  object wrap {

    trait KWrapMessageComponent extends KMessageComponent {
      this: MessageIdComponent =>

      sealed trait KWrapMessage[+M <: KMessage] extends KMessage {
        val message: M
      }

    }

    trait KSignMessageComponent extends KWrapMessageComponent {
      this: MessageIdComponent =>

      sealed trait KSignMessage[+M <: KMessage] extends KWrapMessage[M] {
        val timeStamp: Long
        val dynamicPuzzleProof: BigInt
        val publicKey: PublicKey
        val sign: Seq[Byte]
      }

      trait KSignMessageCompanionBase {
        val signatureAlgorithm: String
        val publicKeyBytes: Int
        val signBytes: Int
      }

      type KSignMessageCompanion <: KSignMessageCompanionBase
      val KSignMessageCompanion: KSignMessageCompanion
    }

    trait KWeakSignMessageComponent extends KSignMessageComponent {
      this: MessageIdComponent
        with PrivateNodeIdComponent
        with AddressComponent =>
      def KWeakSignMessage[M <: KMessage](timeStamp: Long, message: M, publicKey: PublicKey, staticPuzzleProof: BigInt, sign: Seq[Byte]): KWeakSignMessage[M]

      def GenerateKWeakSign[M <: KMessage](timeStamp: Long, address: FromInfo, privateNodeId: PrivateNodeId): Seq[Byte]

      trait KWeakSignMessage[+M <: KMessage] extends KSignMessage[M] {
        final override val messageId = KWeakSignMessageId
      }

      val KWeakSignMessageId: MessageId
    }

    trait KStrongSignMessageComponent extends KSignMessageComponent {
      this: PrivateNodeIdComponent
        with KEnvelopeComponent
        with KAddressComponent
        with MessageIdComponent =>
      def KStrongSignMessage[M <: KMessage](timeStamp: Long, message: M, publicKey: PublicKey, dynamicPuzzleProof: BigInt, sign: Seq[Byte]): KStrongSignMessage[M]

      def GenerateStrongSign(timeStamp: Long, kademliaEnvelope: KEnvelope[KAddress, KMessage], privateNodeId: PrivateNodeId): Seq[Byte]

      trait KStrongSignMessage[M <: KMessage] extends KSignMessage[M] {
        final override val messageId = KStrongSignMessageId
      }

      val KStrongSignMessageId: MessageId
    }

  }

}

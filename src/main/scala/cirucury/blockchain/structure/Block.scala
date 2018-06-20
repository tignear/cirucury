package cirucury.blockchain.structure

abstract class Block {
  val version:Int
  val prevHash:SHA3_256Hash//前のブロックのハッシュ
  val transactionsRootHash:SHA3_256Hash//トランザクションルートハッシュ
  val networkTime:Long//ネットワーク時間
  val difficulty:BigInt//このブロックの難易度
  val beneficiary:Seq[Byte]//PoSに成功したアドレス
  val number:Int//このブロックが何番目か
  val sign:Seq[Byte]//署名
}

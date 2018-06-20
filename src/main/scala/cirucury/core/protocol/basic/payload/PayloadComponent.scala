package cirucury.core.protocol.basic.payload


trait PayloadComponent{
  this: PayloadIdComponent=>
  trait PayloadLike{
    val payloadId:PayloadId
  }
}
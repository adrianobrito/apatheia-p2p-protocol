package org.apatheia.model

import java.nio.ByteBuffer
import org.apatheia.error.PackageDataParsingError
import scala.util.Try
import cats.implicits._
import org.apatheia.validation.NodeIdValidation._
import org.apatheia.validation.ValidationOps._

final case class NodeId(val value: BigInt) extends PackageData {

  def distance(other: NodeId): BigInt = (this.value ^ other.value).abs

  override def toByteArray: Array[Byte] = ByteBuffer.allocate(NodeId.MAX_BYTESIZE).putLong(value.toLong).array()
}

object NodeId extends PackageDataParser[NodeId] {
  val MAX_BYTESIZE: Int = 160

  override def parse(byteArray: Array[Byte]): Either[PackageDataParsingError, NodeId] = Try(
    BigInt.apply(byteArray)
  ).toEither
    .flatMap(bigIntValue => Right(NodeId(bigIntValue)))
    .flatMap(_.validate)
    .leftFlatMap(_ => Left(PackageDataParsingError("Unexpected error while parsing a byte array into a NodeId")))

}

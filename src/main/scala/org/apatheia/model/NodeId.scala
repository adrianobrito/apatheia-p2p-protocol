package org.apatheia.model

import java.nio.ByteBuffer
import org.apatheia.error.PackageDataParsingError
import scala.util.Try
import cats.implicits._
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import javax.print.DocFlavor.CHAR_ARRAY

final case class NodeId(val value: BigInt) extends PackageData {

  def distance(other: NodeId): BigInt = (this.value ^ other.value).abs

  override def toByteArray: Array[Byte] =
    ByteBuffer.allocate(NodeId.BYTESIZE).put(value.toString().getBytes(NodeId.CHARSET)).array()
}

object NodeId extends PackageDataParser[NodeId] {

  val CHARSET: Charset = StandardCharsets.UTF_8

  val BYTESIZE: Int = 20

  override def parse(allocatedByteArray: Array[Byte]): Either[PackageDataParsingError, NodeId] = Try(
    BigInt(new String(allocatedByteArray, CHARSET).trim())
  ).toEither
    .flatMap(bigIntValue => Right(NodeId(bigIntValue)))
    .leftFlatMap(e => Left(PackageDataParsingError(s"Unexpected error while parsing a byte array into a NodeId: ${e}")))

}

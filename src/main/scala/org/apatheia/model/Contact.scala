package org.apatheia.model

import java.nio.ByteBuffer
import cats.data.Cont
import org.apatheia.error.PackageDataParsingError
import java.nio.charset.StandardCharsets

case class Contact(nodeId: NodeId, port: Int, ip: String) extends PackageData {
  override def toByteArray: Array[Byte] =
    Array.concat(nodeId.toByteArray, ByteBuffer.allocate(8).putInt(port).array(), ip.getBytes)
}

object Contact extends PackageDataParser[Contact] {

  private val portByteSize: Int = 8

  override def parse(byteArray: Array[Byte]): Either[PackageDataParsingError, Contact] = NodeId
    .parse(byteArray.take(NodeId.BYTESIZE))
    .flatMap(nodeId =>
      Right(
        Contact(
          nodeId = nodeId,
          port = ByteBuffer.wrap(byteArray.slice(NodeId.BYTESIZE, NodeId.BYTESIZE + portByteSize)).getInt(),
          ip = new String(byteArray.drop(NodeId.BYTESIZE + portByteSize), StandardCharsets.UTF_8)
        )
      )
    )
}

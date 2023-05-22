package org.apatheia.model

import cats.instances.long
import org.apatheia.error.PackageDataParsingError
import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

class NodeIdSpec extends AnyFlatSpec with Matchers with EitherValues {

  behavior of "NodeId"

  it should "calculate the distance between two node IDs correctly" in {
    val nodeId1  = NodeId(20)
    val nodeId2  = NodeId(50)
    val distance = nodeId1.distance(nodeId2)

    distance shouldBe (BigInt(38))
  }

  it should "calculate the distance between two node IDs correctly with negative values" in {
    val nodeId1  = NodeId(-12345)
    val nodeId2  = NodeId(56789)
    val distance = nodeId1.distance(nodeId2)

    distance shouldBe (BigInt(60910))
  }

  it should "parse a byte array BigInt value into a NodeId" in {
    val nodeId       = NodeId(1)
    val byteArray    = nodeId.toByteArray
    val parsedNodeId = NodeId.parse(byteArray)

    parsedNodeId shouldBe Right(nodeId)
    byteArray.size shouldBe NodeId.MAX_BYTESIZE
  }

  it should "not parse an invalid byte array value" in {
    val byteArray = ByteBuffer.allocate(3).put("A".getBytes(NodeId.CHARSET)).array()

    val nodeId = NodeId.parse(byteArray)

    nodeId shouldBe Left(
      PackageDataParsingError(
        "Unexpected error while parsing a byte array into a NodeId: java.lang.NumberFormatException: For input string: \"A\""
      )
    )
  }

}

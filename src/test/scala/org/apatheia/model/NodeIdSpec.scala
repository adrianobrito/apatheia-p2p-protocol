package org.apatheia.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.EitherValues
import org.apatheia.error.PackageDataParsingError

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

  it should "parse a byte array value" in {
    val byteArray = Array.fill[Byte](100)(0)
    val nodeId    = NodeId.parse(byteArray).value

    nodeId.value shouldBe BigInt(0)
  }

  it should "not parse an invalid byte array value" in {
    val byteArray = Array.fill[Byte](10000)(1)
    val nodeId    = NodeId.parse(byteArray)
    nodeId shouldBe Left(PackageDataParsingError("Unexpected error while parsing a byte array into a NodeId"))
  }

}

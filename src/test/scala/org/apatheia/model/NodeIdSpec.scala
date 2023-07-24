package org.apatheia.model

import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

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

}

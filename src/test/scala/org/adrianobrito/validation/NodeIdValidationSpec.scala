package org.adrianobrito.validation

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.adrianobrito.model.NodeId
import org.adrianobrito.validation.NodeIdValidation._
import org.adrianobrito.validation.ValidationOps._

class NodeIdValidationSpec extends AnyFlatSpec with Matchers {

  behavior of "NodeIdValidation"

  it should "consider invalid all NodeId with more than 160bits" in {
    val nodeId1 = NodeId(1234512345123451234L)
    val nodeId2 = NodeId(1234512345123451234L)
  }

  it should "consider valid all NodeId with less than 160bits" in {
    val nodeId1 = NodeId(-12345)
    val nodeId2 = NodeId(56789)
    val distance = nodeId1.distance(nodeId2)
  }

}

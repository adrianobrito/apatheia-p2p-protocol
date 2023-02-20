package org.adrianobrito.validation

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.adrianobrito.model.NodeId
import org.adrianobrito.validation.NodeIdValidation._
import org.adrianobrito.validation.ValidationOps._

class NodeIdValidationSpec extends AnyFlatSpec with Matchers {

  behavior of "NodeIdValidation"

  it should "consider invalid all NodeId with more than 160bits" in {
    val nodeId1 =
      NodeId(
        BigInt(
          "823829389288827382738273827838722372873283826763263726736737263928398239829382983928392839212182787877832"
        )
      )
    val nodeId2 = NodeId(
      BigInt(
        "12345123451234283728738283723827832738273827837827387873283782372878327783278738273512341234512345123451234"
      )
    )

    nodeId1.validate.isLeft shouldBe true
    nodeId2.validate.isLeft shouldBe true
  }

  it should "consider valid all NodeId with less than 160bits" in {
    val nodeId1 = NodeId(-12345)
    val nodeId2 = NodeId(56789)

    nodeId1.validate.isRight shouldBe true
    nodeId2.validate.isRight shouldBe true
  }

}

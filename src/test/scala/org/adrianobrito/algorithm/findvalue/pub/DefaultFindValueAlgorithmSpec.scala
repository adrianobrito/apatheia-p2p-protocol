package org.adrianobrito.algorithm.findvalue.pub

import cats.effect.IO
import cats.effect.Ref
import org.adrianobrito.algorithm.findvalue.FindValueClient
import org.adrianobrito.model.Contact
import org.adrianobrito.model.NodeId
import org.adrianobrito.model.RoutingTable
import org.mockito.Mockito._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

class DefaultFindValueAlgorithmSpec extends AnyWordSpec with Matchers with MockitoSugar {

  "DefaultFindValueAlgorithm" should {

    "return the value if it is found successfully" in {}

    "return an error if the value cannot be found and the maximum number of iterations is reached" in {}

    "return an error if the find node remote request results in unexpected failure" in {}

  }
}

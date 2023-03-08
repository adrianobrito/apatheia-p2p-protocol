package org.apatheia.algorithm.findvalue.pub

import cats.effect.IO
import cats.effect.Ref
import org.apatheia.algorithm.findvalue.FindValueClient
import org.apatheia.model.Contact
import org.apatheia.model.NodeId
import org.apatheia.model.RoutingTable
import org.mockito.Mockito._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.apatheia.model.FindValuePayload
import cats.effect.unsafe.implicits.global
import org.apatheia.error.FindValueError
import org.scalatest.EitherValues

class DefaultFindValueAlgorithmSpec extends AnyWordSpec with Matchers with MockitoSugar with EitherValues {

  "DefaultFindValueAlgorithm" should {
    val nodeId  = NodeId(123)
    val contact = Contact(nodeId, 1110, "1.0.0.99")
    val contacts =
      List(
        contact,
        Contact(NodeId(2), 1111, "1.0.0.99"),
        Contact(NodeId(1), 1112, "1.0.0.99"),
        Contact(NodeId(0), 1113, "1.0.0.99")
      )
    val routingTable = RoutingTable(nodeId = nodeId, contacts = contacts)
    val data         = Array(1.toByte, 0.toByte, 1.toByte, 0.toByte)

    "return the value if it is found successfully" in {
      // Given
      val findValueClient: FindValueClient[IO] = new FindValueClient[IO] {
        override def sendFindValue(targetId: Contact): IO[FindValueClient.FindValueResponse] =
          IO(Right(FindValuePayload(targetId, Some(data))))
      }
      val defaultFindValueAlgorithm: DefaultFindValueAlgorithm[IO] =
        DefaultFindValueAlgorithm[IO](routingTable, findValueClient)
      val targetId = NodeId(888)

      // When
      val result = defaultFindValueAlgorithm.findValue(targetId = targetId)().unsafeRunSync()

      // Then
      result.value shouldBe FindValuePayload(contact, Some(data))
    }

    "return an error if the value cannot be found and the maximum number of iterations is reached" in {
      // Given
      val findValueClient: FindValueClient[IO] = new FindValueClient[IO] {
        override def sendFindValue(targetId: Contact): IO[FindValueClient.FindValueResponse] =
          IO(Left(FindValueError("error")))
      }
      val defaultFindValueAlgorithm: DefaultFindValueAlgorithm[IO] =
        DefaultFindValueAlgorithm[IO](routingTable, findValueClient)
      val targetId = NodeId(888)

      // When
      val result = defaultFindValueAlgorithm.findValue(targetId = targetId)().unsafeRunSync()

      // Then
      result shouldBe Left(FindValueError("Max lookup iterations reached"))
    }

  }
}

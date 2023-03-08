package org.apatheia.algorithm.store.pub

import org.apatheia.model.{Contact, NodeId, RoutingTable, StoreRequest, StoreSuccessThreshold}
import org.scalatest.matchers.should.Matchers
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.concurrent.ScalaFutures
import cats.effect.IO
import cats.implicits._
import org.mockito.Mockito._

import cats.effect.unsafe.implicits.global
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.ArgumentMatchers
import org.apatheia.error.StoreRequestError
import org.apatheia.algorithm.store.StoreClient

class DefaultStoreAlgorithmSpec extends AnyFreeSpec with Matchers with ScalaFutures with MockitoSugar {

  val nodeId = NodeId(123)
  val contacts =
    List(
      Contact(NodeId(2), 1111, "1.0.0.99"),
      Contact(NodeId(1), 1111, "1.0.0.99"),
      Contact(NodeId(0), 1111, "1.0.0.99")
    )
  val routingTable = RoutingTable(nodeId, contacts)
  val threshold    = StoreSuccessThreshold(10)
  val storeClient = new StoreClient[IO] {
    override def sendStoreRequest(storeRequest: StoreRequest): IO[StoreClient.StoreResponse] = IO(
      Right(Contact(nodeId = storeRequest.key, ip = "1.0.0.99", port = 1111))
    )
  }

  "Store Algorithm" - {
    "DefaultStoreAlgorithm" - {
      "should store data in the network" in {
        // Given
        val storeAlgorithm = new DefaultStoreAlgorithm[IO](routingTable, storeClient, threshold)

        // When
        val result = storeAlgorithm.store(nodeId, Array[Byte](1, 3, 4))().unsafeRunSync()

        // Then
        result shouldBe contacts.toSet
      }

      "should stop the store operation if the maximum number of iterations is reached" in {
        val targetContact   = Contact(NodeId(0), 9999, "1.0.0.1")
        val mockStoreClient = mock[StoreClient[IO]]

        when(mockStoreClient.sendStoreRequest(ArgumentMatchers.any[StoreRequest]))
          .thenReturn(IO.pure(Left(StoreRequestError(targetContact))))

        val storeAlgorithm = new DefaultStoreAlgorithm[IO](routingTable, mockStoreClient, maxIterations = 1)
        val result         = storeAlgorithm.store(targetContact.nodeId, Array[Byte](1))().unsafeRunSync()

        // if algorithm wont inf-loop here it means it's f*cking fine
        result shouldBe Set.empty[Contact]
      }

      "should return a list of contacts if at least one successful store response was received" in {
        val mockStoreClient = mock[StoreClient[IO]]
        when(mockStoreClient.sendStoreRequest(ArgumentMatchers.any[StoreRequest]))
          .thenReturn(IO.pure(Right(Contact(NodeId(22), 8081, "localhost"))))

        val storeAlgorithm = new DefaultStoreAlgorithm[IO](routingTable, mockStoreClient)
        val result         = storeAlgorithm.store(NodeId(99), Array[Byte]())().unsafeRunSync()

        result shouldBe Set(Contact(NodeId(22), 8081, "localhost"))
      }

      "should return a set of failed response contacts" in {
        val nodeId       = NodeId(9999)
        val routingTable = RoutingTable(nodeId, List.empty)
        val storeClient = new StoreClient[IO] {
          override def sendStoreRequest(request: StoreRequest): IO[StoreClient.StoreResponse] =
            IO.pure(Left(StoreRequestError(Contact(NodeId(2), 1111, "1.0.0.99"))))
        }
        val storeAlgorithm = new DefaultStoreAlgorithm[IO](routingTable, storeClient)
        storeAlgorithm.store(NodeId(3), Array.empty[Byte])().map { contacts =>
          contacts shouldBe a[Set[_]]
          contacts.size shouldBe routingTable.contacts.size
        }
      }

    }
  }
}

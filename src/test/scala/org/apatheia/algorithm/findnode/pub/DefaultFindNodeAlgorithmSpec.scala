package org.apatheia.algorithm.findnode.pub

import cats.effect.IO
import cats.Applicative
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._

import cats.effect.unsafe.implicits.global
import org.mockito.ArgumentMatchers
import org.apatheia.algorithm.findnode.FindNodeClient
import org.apatheia.model.Contact
import org.apatheia.model.NodeId
import org.apatheia.model.RoutingTable

class DefaultFindNodeAlgorithmSpec extends AnyFlatSpec with Matchers with MockitoSugar {

  def mockFindNodeClient(expectedContacts: Set[Contact]) =
    new FindNodeClient[IO] {
      override def requestContacts(contact: Contact): IO[List[Contact]] =
        IO.pure(expectedContacts.toList)
    }

  behavior of "DefaultFindNodeAlgorithm"

  it should "return the closest contacts list sorted by distance to targetId" in {
    val nodeId1 = NodeId(1)
    val nodeId2 = NodeId(2)
    val nodeId3 = NodeId(3)
    val nodeId4 = NodeId(4)

    val contact1 = Contact(nodeId = nodeId1, ip = "0.0.0.0", port = 8080)
    val contact2 = Contact(nodeId = nodeId2, ip = "0.0.0.0", port = 8080)
    val contact3 = Contact(nodeId = nodeId3, ip = "0.0.0.0", port = 8080)
    val contact4 = Contact(nodeId = nodeId4, ip = "0.0.0.0", port = 8080)

    val routingTable = RoutingTable(
      nodeId = nodeId1,
      contacts = List(contact2, contact3, contact4)
    )

    val expectedContacts = Set(contact3, contact2, contact4)

    val findNodeAlgorithm = new DefaultFindNodeAlgorithm[IO](
      findNodeClient = mockFindNodeClient(expectedContacts)
    )

    val result = findNodeAlgorithm
      .findNode(
        routingTable = routingTable,
        targetId = nodeId1,
        maxIterations = 1
      )
      .unsafeRunSync()

    result shouldBe expectedContacts
  }

  it should "call findClosestContacts on the routing table" in {
    val routingTable      = mock[RoutingTable]
    val findNodeClient    = mock[FindNodeClient[IO]]
    val findNodeAlgorithm = DefaultFindNodeAlgorithm[IO](findNodeClient)

    when(routingTable.findClosestContacts(NodeId(1)))
      .thenReturn(List(Contact(NodeId(2), 12345, "localhost")))

    findNodeAlgorithm.findNode(routingTable, NodeId(1), 1)
    verify(routingTable).findClosestContacts(NodeId(1))
  }

  it should "send a find node request for every contact in the closestContacts list" in {
    val routingTable      = mock[RoutingTable]
    val findNodeClient    = mock[FindNodeClient[IO]]
    val findNodeAlgorithm = DefaultFindNodeAlgorithm[IO](findNodeClient)

    when(routingTable.findClosestContacts(NodeId(1))).thenReturn(
      List(
        Contact(NodeId(2), 12345, "localhost"),
        Contact(NodeId(3), 12335, "localhost")
      )
    )

    when(
      findNodeClient.requestContacts(Contact(NodeId(2), 12345, "localhost"))
    ).thenReturn(IO(List(Contact(NodeId(4), 12345, "localhost"))))

    when(
      findNodeClient.requestContacts(Contact(NodeId(3), 12335, "localhost"))
    ).thenReturn(IO(List(Contact(NodeId(5), 12345, "localhost"))))

    findNodeAlgorithm.findNode(routingTable, NodeId(1), 1).unsafeRunSync()

    verify(findNodeClient).requestContacts(
      Contact(NodeId(2), 12345, "localhost")
    )
    verify(findNodeClient).requestContacts(
      Contact(NodeId(3), 12335, "localhost")
    )
  }

  it should "not go more iterations than necessary" in {
    val mockFindNodeClient = mock[FindNodeClient[IO]]
    val algorithm          = DefaultFindNodeAlgorithm[IO](mockFindNodeClient)
    val routingTable       = RoutingTable(nodeId = NodeId(1), contacts = List.empty)
    val maxIterations      = 3
    val targetNodeId       = NodeId(2)
    val targetContact      = Contact(targetNodeId, 12345, "localhost")

    when(
      mockFindNodeClient
        .requestContacts(ArgumentMatchers.any[Contact]())
    ).thenReturn(IO.pure(List.empty[Contact]))

    val result =
      algorithm
        .findNode(targetId = targetNodeId, routingTable = routingTable, maxIterations = maxIterations)
        .unsafeRunSync()

    result shouldBe Set.empty
  }

}

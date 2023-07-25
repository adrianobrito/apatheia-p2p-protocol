package org.apatheia.model

import org.apatheia.model.{Contact, NodeId, RoutingTable}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RoutingTableSpec extends AnyFlatSpec with Matchers {

  "RoutingTable" should "add a new contact" in {
    val nodeId          = NodeId(BigInt(0))
    val contact         = Contact(NodeId(BigInt(1)), 8888, "127.0.0.1")
    val routingTable    = RoutingTable(nodeId, Set.empty)
    val newRoutingTable = routingTable.addContact(contact)

    newRoutingTable.contacts should contain(contact)
  }

  it should "find closest contacts" in {
    val nodeId       = NodeId(BigInt(0))
    val contact1     = Contact(NodeId(BigInt(1)), 8888, "127.0.0.1")
    val contact2     = Contact(NodeId(BigInt(2)), 8888, "127.0.0.1")
    val routingTable = RoutingTable(nodeId, Set(contact1, contact2))

    val closestContacts = routingTable.findClosestContacts(NodeId(BigInt(3)))
    closestContacts should contain theSameElementsAs Set(contact2, contact1)
  }

  it should "update a contact" in {
    val nodeId              = NodeId(BigInt(0))
    val contact             = Contact(NodeId(BigInt(1)), 8888, "127.0.0.1")
    val routingTable        = RoutingTable(nodeId, Set(contact))
    val updatedContact      = Contact(NodeId(BigInt(1)), 8889, "127.0.0.1")
    val updatedRoutingTable = routingTable.updateContact(updatedContact)

    updatedRoutingTable.contacts should contain(updatedContact)
    updatedRoutingTable.contacts.size should be(1)
  }

}

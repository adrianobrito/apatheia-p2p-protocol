package org.adrianobrito.model

import org.adrianobrito.model.{Contact, NodeId, RoutingTable}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RoutingTableSpec extends AnyFlatSpec with Matchers {

  "RoutingTable" should "add a new contact" in {
    val nodeId = NodeId(BigInt(0))
    val contact = Contact(NodeId(BigInt(1)), "127.0.0.1", 8888)
    val routingTable = RoutingTable(nodeId, List.empty)
    val newRoutingTable = routingTable.addContact(contact)

    newRoutingTable.contacts should contain(contact)
  }

  it should "find closest contacts" in {
    val nodeId = NodeId(BigInt(0))
    val contact1 = Contact(NodeId(BigInt(1)), "127.0.0.1", 8888)
    val contact2 = Contact(NodeId(BigInt(2)), "127.0.0.1", 8888)
    val routingTable = RoutingTable(nodeId, List(contact1, contact2))

    val closestContacts = routingTable.findClosestContacts(NodeId(BigInt(3)))
    closestContacts should contain theSameElementsAs List(contact2, contact1)
  }

  it should "update a contact" in {
    val nodeId = NodeId(BigInt(0))
    val contact = Contact(NodeId(BigInt(1)), "127.0.0.1", 8888)
    val routingTable = RoutingTable(nodeId, List(contact))
    val updatedContact = Contact(NodeId(BigInt(1)), "127.0.0.1", 8889)
    val updatedRoutingTable = routingTable.updateContact(updatedContact)

    updatedRoutingTable.contacts should contain(updatedContact)
    updatedRoutingTable.contacts.length should be(1)
  }

}

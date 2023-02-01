package org.adrianobrito.model

case class RoutingTable(
    nodeId: NodeId,
    contacts: List[Contact],
    k: Int = 20
) {

  def addContact(contact: Contact): RoutingTable = {
    RoutingTable(nodeId, contact :: contacts, k)
  }
  def findClosestContacts(targetId: NodeId): List[Contact] = {
    val sortedContacts =
      contacts.sortBy(c => (c.nodeId.value ^ targetId.value).abs)
    sortedContacts.take(k)
  }
  def updateContact(contact: T): RoutingTable = {
    // code to update contact
    this
  }
}

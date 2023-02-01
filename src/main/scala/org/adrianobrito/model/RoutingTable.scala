package org.adrianobrito.model

case class RoutingTable(
    nodeId: NodeId,
    contacts: List[Contact],
    k: Int = 20
) {

  private val contactsMap: Map[NodeId, Contact] = contacts.groupMap(_.nodeId)(_)

  def addContact(contact: Contact): RoutingTable = {
    RoutingTable(nodeId, contact :: contacts, k)
  }

  def findClosestContacts(targetId: NodeId): List[Contact] =
    contacts.sortBy(_.nodeId.distance(targetId)).take(k)

  def updateContact(contact: Contact): RoutingTable = {
    // code to update contact
    this
  }
}

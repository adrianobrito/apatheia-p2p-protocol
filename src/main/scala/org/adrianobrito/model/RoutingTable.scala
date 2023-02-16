package org.adrianobrito.model

final case class RoutingTable(
  nodeId: NodeId,
  contacts: List[Contact],
  k: Int = 20
) {

  def addContact(contact: Contact): RoutingTable =
    RoutingTable(nodeId, contact :: contacts, k)

  def findClosestContacts(targetId: NodeId): List[Contact] =
    contacts.sortBy(_.nodeId.distance(targetId)).take(k)

  def updateContact(contact: Contact): RoutingTable = RoutingTable(
    nodeId = nodeId,
    contacts = contact :: contacts.filter(_.nodeId != contact.nodeId),
    k = k
  )

}

package org.apatheia.model

case class RoutingTable(
  nodeId: NodeId,
  contacts: Set[Contact],
  k: Int = 20
) {

  def addContacts(contactSet: Set[Contact]): RoutingTable =
    RoutingTable(nodeId, contacts ++ contactSet, k)

  def addContact(contact: Contact): RoutingTable =
    RoutingTable(nodeId, contacts + contact, k)

  def findClosestContacts(targetId: NodeId): Set[Contact] =
    contacts.toList.sortBy(_.nodeId.distance(targetId)).take(k).toSet

  def updateContact(contact: Contact): RoutingTable = RoutingTable(
    nodeId = nodeId,
    contacts = contacts.filter(_.nodeId != contact.nodeId) + contact,
    k = k
  )

}

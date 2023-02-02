package org.adrianobrito.algorithms.findnode

import org.adrianobrito.model.RoutingTable
import org.adrianobrito.model.NodeId
import org.adrianobrito.model.Contact
import cats.effect.kernel.Async
import cats.implicits._

case class DefaultFindNodeAlgorithm[F[_]: Async](
    findNodeClient: FindNodeClient[F]
) extends FindNodeAlgorithm {

  override def findNode(
      routingTable: RoutingTable,
      targetId: NodeId,
      maxIterations: Int
  ): List[Contact] = {
    val closestContacts = routingTable.findClosestContacts(targetId)
    retryFindNodeRequest(
      iteration = maxIterations,
      routingTable = routingTable,
      closestContacts = closestContacts,
      targetId = targetId
    )
  }

  private def retryFindNodeRequest(
      iteration: Int,
      routingTable: RoutingTable,
      closestContacts: List[Contact],
      targetId: NodeId
  ): List[Contact] = {
    if (iteration == 0) {
      List.empty
    } else {
      closestContacts
        .find(_.nodeId.value == targetId.value)
        .map(List(_))
        .getOrElse(
          retryFindNodeRequest(
            iteration = iteration - 1,
            routingTable = routingTable,
            targetId = targetId,
            closestContacts = closestContacts
              .flatMap(sendFindNodeRequest)
              .sortBy(_.nodeId.distance(targetId))
              .take(routingTable.k)
          )
        )
    }
  }

  private def sendFindNodeRequest(nodeContact: Contact): List[Contact] = ???

}

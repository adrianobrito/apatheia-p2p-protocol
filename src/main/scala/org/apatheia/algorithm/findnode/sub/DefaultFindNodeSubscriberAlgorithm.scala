package org.apatheia.algorithm.findnode.sub

import cats.effect.kernel.Async
import org.apatheia.model.NodeId
import org.apatheia.model.RoutingTable
import org.apatheia.model.Contact

final case class DefaultFindNodeSubscriberAlgorithm[F[_]: Async]() extends FindNodeSubscriberAlgorithm[F] {
  override def findNode(target: NodeId, routingTable: RoutingTable): F[List[Contact]] =
    Async[F].pure(routingTable.findClosestContacts(targetId = target))
}

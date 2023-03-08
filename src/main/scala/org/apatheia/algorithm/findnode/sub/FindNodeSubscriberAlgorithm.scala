package org.apatheia.algorithm.findnode.sub

import org.apatheia.model.NodeId
import org.apatheia.model.RoutingTable
import org.apatheia.model.Contact

trait FindNodeSubscriberAlgorithm[F[_]] {
  def findNode(target: NodeId, routingTable: RoutingTable): F[List[Contact]]
}

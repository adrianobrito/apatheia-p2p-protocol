package org.apatheia.algorithm.findnode.pub

import org.apatheia.model.RoutingTable
import org.apatheia.model.NodeId
import org.apatheia.model.Contact

trait FindNodeAlgorithm[F[_]] {

  def findNode(
    routingTable: RoutingTable,
    targetId: NodeId
  ): F[Set[Contact]]

}

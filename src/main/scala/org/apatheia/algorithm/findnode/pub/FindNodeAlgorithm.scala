package org.apatheia.algorithm.findnode.pub

import org.apatheia.model.RoutingTable
import org.apatheia.model.NodeId
import org.apatheia.model.Contact

trait FindNodeAlgorithm[F[_]] {

  def findNode(
    routingTable: RoutingTable,
    targetId: NodeId,
    maxIterations: Int = 20
  ): F[Set[Contact]]

}

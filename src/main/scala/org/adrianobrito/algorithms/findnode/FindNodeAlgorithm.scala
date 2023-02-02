package org.adrianobrito.algorithms.findnode

import org.adrianobrito.model.RoutingTable
import org.adrianobrito.model.NodeId
import org.adrianobrito.model.Contact

trait FindNodeAlgorithm {

  def findNode(
      routingTable: RoutingTable,
      targetId: NodeId,
      maxIterations: Int = 20
  ): List[Contact]

}

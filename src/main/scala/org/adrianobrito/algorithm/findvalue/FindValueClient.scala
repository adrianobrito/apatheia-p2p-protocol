package org.adrianobrito.algorithm.findvalue

import org.adrianobrito.model.NodeId
import org.adrianobrito.model.FindValuePayload
import org.adrianobrito.error.FindValueError

trait FindValueClient[F[_]] {
  def sendFindNode(targetId: NodeId): F[FindValueClient.FindValueResponse]
}

object FindValueClient {
  type FindValueResponse = Either[FindValueError, FindValuePayload]
}

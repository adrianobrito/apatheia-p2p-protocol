package org.adrianobrito.algorithm.findvalue.pub

import org.adrianobrito.model.NodeId
import org.adrianobrito.model.FindValuePayload
import org.adrianobrito.error.FindValueError

trait FindValueAlgorithm[F[_]] {

  def findValue(targetId: NodeId)(iterations: Int): F[FindValueAlgorithm.FindValueResult]

}

object FindValueAlgorithm {
  type FindValueResult = Either[FindValueError, FindValuePayload]
}

package org.apatheia.algorithm.findvalue.pub

import org.apatheia.model.NodeId
import org.apatheia.model.FindValuePayload
import org.apatheia.error.FindValueError

trait FindValueAlgorithm[F[_]] {

  def findValue(targetId: NodeId)(iterations: Int): F[FindValueAlgorithm.FindValueResult]

}

object FindValueAlgorithm {
  type FindValueResult = Either[FindValueError, FindValuePayload]
}

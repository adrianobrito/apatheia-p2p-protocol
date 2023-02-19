package org.adrianobrito.algorithm.findvalue

import org.adrianobrito.model.FindValuePayload
import org.adrianobrito.error.FindValueError
import org.adrianobrito.model.Contact

trait FindValueClient[F[_]] {
  def sendFindValue(targetId: Contact): F[FindValueClient.FindValueResponse]
}

object FindValueClient {
  type FindValueResponse = Either[FindValueError, FindValuePayload]
}

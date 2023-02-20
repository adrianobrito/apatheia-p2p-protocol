package org.apatheia.algorithm.findvalue

import org.apatheia.model.FindValuePayload
import org.apatheia.error.FindValueError
import org.apatheia.model.Contact

trait FindValueClient[F[_]] {
  def sendFindValue(targetId: Contact): F[FindValueClient.FindValueResponse]
}

object FindValueClient {
  type FindValueResponse = Either[FindValueError, FindValuePayload]
}

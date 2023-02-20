package org.apatheia.algorithm.findnode

import org.apatheia.model.Contact

trait FindNodeClient[F[_]] {
  def requestContacts(nodeContact: Contact): F[List[Contact]]
}

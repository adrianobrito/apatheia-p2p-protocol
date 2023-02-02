package org.adrianobrito.algorithms.findnode

import org.adrianobrito.model.Contact

trait FindNodeClient[F[_]] {
  def requestNodeContacts(nodeContact: Contact): F[List[Contact]]
}

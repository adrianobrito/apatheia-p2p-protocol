package org.apatheia.algorithm.findnode

import org.apatheia.model.Contact

trait FindNodeClient[F[_]] {
  def requestContacts(remoteContact: Contact, target: NodeId): F[List[Contact]]
}

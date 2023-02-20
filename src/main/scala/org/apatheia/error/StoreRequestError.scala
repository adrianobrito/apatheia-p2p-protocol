package org.apatheia.error

import org.apatheia.model.Contact

final case class StoreRequestError(contact: Contact) extends Error {
  override def message: String = s"Error while sending STORE request to Contact #${contact.nodeId.value}"
}

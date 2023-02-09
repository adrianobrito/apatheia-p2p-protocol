package org.adrianobrito.error

import org.adrianobrito.model.Contact

final case class StoreRequestError(contact: Contact) extends Error {
  override def message: String = "Error while sending STORE request"
}

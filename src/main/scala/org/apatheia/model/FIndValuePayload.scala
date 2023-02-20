package org.adrianobrito.model

import org.adrianobrito.model.Contact

final case class FindValuePayload(contact: Contact, value: Option[Array[Byte]])

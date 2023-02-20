package org.apatheia.model

import org.apatheia.model.Contact

final case class FindValuePayload(contact: Contact, value: Option[Array[Byte]])

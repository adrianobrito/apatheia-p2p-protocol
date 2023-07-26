package org.apatheia.error

import org.apatheia.model.Contact

final case class FindValueError(message: String, contacts: Set[Contact]) extends Error

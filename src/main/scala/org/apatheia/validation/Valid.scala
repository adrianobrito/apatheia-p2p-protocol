package org.adrianobrito.validation

import org.adrianobrito.error

trait Valid[T] {
  def validate(t: T): Either[error.Error, T]
}

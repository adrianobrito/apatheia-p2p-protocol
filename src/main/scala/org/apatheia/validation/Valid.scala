package org.apatheia.validation

import org.apatheia.error

trait Valid[T] {
  def validate(t: T): Either[error.Error, T]
}

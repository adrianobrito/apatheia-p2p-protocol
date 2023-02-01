package org.adrianobrito.validation

object ValidationOps {
  implicit class ValidationOps[A: Valid](a: A) {
    def validate(implicit valid: Valid[A]) = valid.validate(a)
  }
}

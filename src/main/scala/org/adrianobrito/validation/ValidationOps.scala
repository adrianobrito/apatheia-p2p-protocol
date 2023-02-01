package org.adrianobrito.validation

object ValidationOps {
  implicit class ValidationOpsClass[A: Valid](a: A) {
    def validate(implicit valid: Valid[A]) = valid.validate(a)
  }
}

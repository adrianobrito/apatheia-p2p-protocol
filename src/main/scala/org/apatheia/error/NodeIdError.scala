package org.adrianobrito.error

trait NodeIdError extends Error

object NodeIdErrors {

  val INVALID_SIZE: NodeIdError = new NodeIdError {
    def message: String =
      "Invalid size. Node id should have size smaller than 160 bits"
  }

}

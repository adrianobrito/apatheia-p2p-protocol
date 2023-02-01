package org.adrianobrito.validation

import org.adrianobrito.validation.Valid
import org.adrianobrito.model.NodeId
import org.adrianobrito.error.NodeIdErrors
import org.adrianobrito.error.NodeIdError
import org.adrianobrito.error

class NodeIdValidation extends Valid[NodeId] {

  override def validate(t: NodeId): Either[error.Error, NodeId] = if (
    t.value.bitCount > 160
  ) {
    Right(t)
  } else {
    Left(NodeIdErrors.INVALID_SIZE)
  }

}

object NodeIdValidation {
  implicit val nodeIdValidation: Valid[NodeId] =
    new NodeIdValidation
}

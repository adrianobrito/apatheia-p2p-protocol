package org.apatheia.validation

import org.apatheia.validation.Valid
import org.apatheia.model.NodeId
import org.apatheia.error.NodeIdErrors
import org.apatheia.error.NodeIdError
import org.apatheia.error

class NodeIdValidation extends Valid[NodeId] {

  override def validate(t: NodeId): Either[error.Error, NodeId] = if (t.value.bitCount <= 160) {
    Right(t)
  } else {
    Left(NodeIdErrors.INVALID_SIZE)
  }

}

object NodeIdValidation {
  implicit val nodeIdValidation: Valid[NodeId] =
    new NodeIdValidation
}

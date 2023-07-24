package org.apatheia.algorithm.store.pub

import org.apatheia.model.Contact
import org.apatheia.model.NodeId

trait StoreAlgorithm[F[_]] {

  def store(key: NodeId, value: Array[Byte]): F[Set[Contact]]

}

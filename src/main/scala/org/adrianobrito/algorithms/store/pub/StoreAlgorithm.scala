package org.adrianobrito.algorithms.store.pub

import org.adrianobrito.model.StoreRequest

trait StoreAlgorithm[F[_]] {
  def store(request: StoreRequest): F[Unit]
}

package org.adrianobrito.algorithms.store

trait StoreClient[F[_]] {
  def sendStoreRequest(): F[Unit]
}

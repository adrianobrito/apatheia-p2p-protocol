package org.adrianobrito.algorithms.store.pub

import cats.effect.kernel.Async
import org.adrianobrito.algorithms.store.pub.StoreAlgorithm
import org.adrianobrito.model.StoreRequest

class DefaultStoreAlgorithm[F[_]: Async] extends StoreAlgorithm[F] {

  override def store(request: StoreRequest): F[Unit] = ???

}

package org.adrianobrito.algorithms.store.pub

import cats.effect.kernel.Async
import org.adrianobrito.algorithms.store.StoreAlgorithm

class DefaultStoreAlgorithm[F[_]: Async] extends StoreAlgorithm[F] {}

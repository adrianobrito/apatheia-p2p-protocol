package org.adrianobrito.algorithms.store

import org.adrianobrito.model.StoreRequest
import org.adrianobrito.error.StoreRequestError
import org.adrianobrito.model.Contact

trait StoreClient[F[_]] {
  def sendStoreRequest(storeRequest: StoreRequest): F[StoreClient.StoreResponse]
}

object StoreClient {
  type StoreResponse = Either[StoreRequestError, Contact]
}

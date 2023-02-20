package org.adrianobrito.algorithm.store

import org.adrianobrito.model.StoreRequest
import org.adrianobrito.error.StoreRequestError
import org.adrianobrito.model.Contact

trait StoreClient[F[_]] {
  def sendStoreRequest(storeRequest: StoreRequest): F[StoreClient.StoreResponse]
}

object StoreClient {
  type StoreResponse = Either[StoreRequestError, Contact]
}

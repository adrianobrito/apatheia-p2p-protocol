package org.apatheia.algorithm.store

import org.apatheia.model.StoreRequest
import org.apatheia.error.StoreRequestError
import org.apatheia.model.Contact

trait StoreClient[F[_]] {
  def sendStoreRequest(storeRequest: StoreRequest): F[StoreClient.StoreResponse]
}

object StoreClient {
  type StoreResponse = Either[StoreRequestError, Contact]
}

package org.apatheia.algorithm.store.pub

import cats.effect.kernel.Async
import org.apatheia.model.StoreRequest
import org.apatheia.model.RoutingTable
import org.apatheia.algorithm.store.StoreClient
import org.apatheia.algorithm.store.StoreClient.StoreResponse
import org.apatheia.model.NodeId
import cats.implicits._
import org.apatheia.model.StoreSuccessThreshold
import org.apatheia.model.Contact

class DefaultStoreAlgorithm[F[_]: Async](
  routingTable: RoutingTable,
  storeClient: StoreClient[F],
  threshold: StoreSuccessThreshold = StoreSuccessThreshold(1),
  maxIterations: Int = 20
) extends StoreAlgorithm[F] {

  private def sendStoreRequests(key: NodeId, data: Array[Byte]): F[List[StoreResponse]] = routingTable
    .findClosestContacts(targetId = key)
    .map(contact => StoreRequest(key = contact.nodeId, value = data))
    .traverse(storeClient.sendStoreRequest)

  private def splitResponsesByStatus(responses: List[StoreResponse]): (List[StoreResponse], List[StoreResponse]) =
    (responses.filter(_.isLeft), responses.filter(_.isRight))

  private def toContacts(responses: F[List[StoreResponse]]): F[Set[Contact]] =
    responses.flatMap(r => Async[F].pure(r.flatMap(_.toList).toSet))

  override def store(key: NodeId, data: Array[Byte])(iterations: Int = maxIterations): F[Set[Contact]] = {
    val responses: F[(List[StoreResponse], List[StoreResponse])] =
      sendStoreRequests(key, data).flatMap { r =>
        Async[F].delay(splitResponsesByStatus(r))
      }
    val failedResponses             = responses._1F
    val successfulResponses         = responses._2F
    val successfulResponsesContacts = toContacts(successfulResponses)
    val failedResponsesContacts     = toContacts(failedResponses)

    failedResponses.flatMap { fResponses =>
      if (fResponses.size > threshold.value && iterations > 0) {
        store(key, data)(iterations - 1)
      } else if (iterations == 0) {
        failedResponsesContacts
      } else {
        successfulResponsesContacts
      }
    }
  }

}

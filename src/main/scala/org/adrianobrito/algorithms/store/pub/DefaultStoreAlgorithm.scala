package org.adrianobrito.algorithms.store.pub

import cats.effect.kernel.Async
import org.adrianobrito.algorithms.store.pub.StoreAlgorithm
import org.adrianobrito.model.StoreRequest
import org.adrianobrito.model.RoutingTable
import org.adrianobrito.algorithms.store.StoreClient
import org.adrianobrito.algorithms.store.StoreClient.StoreResponse
import org.adrianobrito.model.NodeId
import cats.implicits._
import org.adrianobrito.model.StoreSuccessThreshold

class DefaultStoreAlgorithm[F[_]: Async](
  routingTable: RoutingTable,
  storeClient: StoreClient[F],
  threshold: StoreSuccessThreshold = StoreSuccessThreshold(1)
) extends StoreAlgorithm[F] {

  override def store(key: NodeId, value: Array[Byte]): F[Unit] = {
    val responseEffects: F[List[StoreResponse]] = routingTable
      .findClosestContacts(targetId = key)
      .map(contact => StoreRequest(key = contact.nodeId, value = value))
      .traverse(storeClient.sendStoreRequest)

    responseEffects.flatMap { responses => }

    for {
      closestContacts    <- Async[F].pure(routingTable.findClosestContacts(targetId = key))
      storeRequests       = closestContacts.map(contact => StoreRequest(key = contact.nodeId, value = value))
      storeResponses     <- storeRequests.traverse(storeClient.sendStoreRequest)
      successfulResponses = storeResponses.filter(_.isRight)
      failedResponses     = storeResponses.filter(_.isLeft)
    } yield ()
  }

}

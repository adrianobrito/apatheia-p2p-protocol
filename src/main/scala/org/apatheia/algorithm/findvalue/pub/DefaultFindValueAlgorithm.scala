package org.apatheia.algorithm.findvalue.pub

import cats.effect.kernel.Async
import org.apatheia.model.NodeId
import org.apatheia.model.RoutingTable

import cats.implicits._
import org.apatheia.algorithm.findvalue.FindValueClient
import org.apatheia.algorithm.findvalue.FindValueClient.FindValueResponse
import org.apatheia.algorithm.findvalue.pub.FindValueAlgorithm.FindValueResult
import org.apatheia.error.FindValueError

case class DefaultFindValueAlgorithm[F[_]: Async](
  routingTable: RoutingTable,
  findValueClient: FindValueClient[F],
  maxIterations: Int = 20
) extends FindValueAlgorithm[F] {

  private def sendFindValueRequest(targetId: NodeId): F[List[FindValueResponse]] = routingTable
    .findClosestContacts(targetId = targetId)
    .traverse(findValueClient.sendFindValue)

  private def splitResponsesByStatus(
    responses: List[FindValueResponse]
  ): (List[FindValueResponse], List[FindValueResponse]) =
    (responses.filter(_.isLeft), responses.filter(_.isRight))

  override def findValue(targetId: NodeId)(iterations: Int = maxIterations): F[FindValueResult] = {
    val responses: F[(List[FindValueResponse], List[FindValueResponse])] =
      sendFindValueRequest(targetId).flatMap { r =>
        Async[F].delay(splitResponsesByStatus(r))
      }
    val failedResponses     = responses._1F
    val successfulResponses = responses._2F

    failedResponses.flatMap(fResponses =>
      if (fResponses.isEmpty) {
        successfulResponses.flatMap(sResponses => Async[F].pure(sResponses.head))
      } else if (iterations == 0) {
        Async[F].pure(Left(FindValueError("Max lookup iterations reached")))
      } else {
        findValue(targetId)(iterations - 1)
      }
    )

  }

}

package org.apatheia.store

import org.apatheia.model.NodeId
import scala.collection.immutable.HashMap

case class ApatheiaKeyValueStore[T, X](map: HashMap[T, X]) extends KeyValueStore[T, X] {

  override def get(key: T): Option[X] = map.get(key)
  override def remove(key: T): KeyValueStore[T, X] =
    ApatheiaKeyValueStore(map.removed(key))
  override def put(
    key: T,
    value: X
  ): KeyValueStore[T, X] = ApatheiaKeyValueStore(
    map + (key -> value)
  )

  override def iterator: Iterator[T] = map.keySet.iterator
}

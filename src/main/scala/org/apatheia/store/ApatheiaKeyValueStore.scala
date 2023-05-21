package org.apatheia.store

import org.apatheia.model.NodeId
import scala.collection.immutable.HashMap

case class ApatheiaKeyValueStore[T](map: HashMap[T, Array[Byte]]) extends KeyValueStore[T, Array[Byte]] {

  override def get(key: T): Option[Array[Byte]] = map.get(key)
  override def remove(key: T): KeyValueStore[T, Array[Byte]] =
    ApatheiaKeyValueStore(map.removed(key))
  override def put(
    key: T,
    value: Array[Byte]
  ): KeyValueStore[T, Array[Byte]] = ApatheiaKeyValueStore(
    map + (key -> value)
  )

  override def iterator: Iterator[T] = map.keySet.iterator
}

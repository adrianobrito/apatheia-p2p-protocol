package org.apatheia.store

import org.apatheia.model.NodeId
import scala.collection.immutable.HashMap

case class ApatheiaKeyValueStore(map: HashMap[NodeId, Array[Byte]]) extends KeyValueStore[NodeId, Array[Byte]] {

  override def get(key: NodeId): Option[Array[Byte]] = map.get(key)
  override def remove(key: NodeId): KeyValueStore[NodeId, Array[Byte]] =
    ApatheiaKeyValueStore(map.removed(key))
  override def put(
    key: NodeId,
    value: Array[Byte]
  ): KeyValueStore[NodeId, Array[Byte]] = ApatheiaKeyValueStore(
    map + (key -> value)
  )

  override def iterator: Iterator[NodeId] = map.keySet.iterator
}

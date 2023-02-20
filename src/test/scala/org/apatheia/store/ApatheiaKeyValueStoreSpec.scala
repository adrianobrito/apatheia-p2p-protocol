package org.apatheia.store

import org.scalatest._
import scala.collection.immutable.HashMap
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.apatheia.model.NodeId

class ApatheiaKeyValueStoreSpec extends AnyFlatSpec with Matchers {
  behavior of "ApatheiaKeyValueStore"

  it should "get a value for a given key" in {
    val nodeId = NodeId(12345)
    val value  = Array[Byte](1, 2, 3)
    val map    = HashMap[NodeId, Array[Byte]](nodeId -> value)
    val store  = ApatheiaKeyValueStore(map)

    store.get(nodeId) should be(Some(value))
  }

  it should "return None for a key that doesn't exist" in {
    val nodeId = NodeId(12345)
    val store  = ApatheiaKeyValueStore(HashMap[NodeId, Array[Byte]]())

    store.get(nodeId) should be(None)
  }

  it should "remove a key-value pair for a given key" in {
    val nodeId = NodeId(12345)
    val value  = Array[Byte](1, 2, 3)
    val map    = HashMap[NodeId, Array[Byte]](nodeId -> value)
    val store  = ApatheiaKeyValueStore(map)

    store.remove(nodeId).get(nodeId) should be(None)
  }

  it should "put a new key-value pair in the store" in {
    val nodeId = NodeId(12345)
    val value  = Array[Byte](1, 2, 3)
    val store  = ApatheiaKeyValueStore(HashMap[NodeId, Array[Byte]]())

    store.put(nodeId, value).get(nodeId) should be(Some(value))
  }

  it should "iterate over all keys in the store" in {
    val nodeId1 = NodeId(12345)
    val value1  = Array[Byte](1, 2, 3)
    val nodeId2 = NodeId(67890)
    val value2  = Array[Byte](4, 5, 6)
    val map     = HashMap[NodeId, Array[Byte]](nodeId1 -> value1, nodeId2 -> value2)
    val store   = ApatheiaKeyValueStore(map)

    store.iterator.toSet should be(Set(nodeId1, nodeId2))
  }
}

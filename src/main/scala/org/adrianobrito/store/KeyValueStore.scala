package org.adrianobrito.store

trait KeyValueStore[K, V] {
  def put(key: K, value: V): KeyValueStore[K, V]
  def get(key: K): Option[V]
  def remove(key: K): KeyValueStore[K, V]
  def iterator: Iterator[K]
}

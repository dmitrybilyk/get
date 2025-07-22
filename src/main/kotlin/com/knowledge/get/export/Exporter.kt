package com.knowledge.get.export

abstract class Exporter<V, C: Collection<V>> {
    abstract fun serializeItem(item: V): String

    abstract fun serializeCollection(collection: C): String

    fun export(items: List<V>): String {
        return items.joinToString (separator = "\n") { serializeItem(it)}
    }
}
package com.knowledge.get.export

class CsvListExporter<V : Any>(
    private val rowSerializer: (V) -> String
) : Exporter<V, List<V>>() {


    override fun serializeCollection(collection: List<V>): String {
        return collection.joinToString("\n") { rowSerializer(it) }
    }

    override fun serializeItem(item: V): String = rowSerializer(item)
}
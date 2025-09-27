package com.knowledge.get.patterns.structural

import java.lang.Thread.sleep

fun main() {
    val itemProcessor = ItemProcessorProxy(ItemProcessorImpl())
    itemProcessor.processItem(true)
}

interface ItemProcessor {
    fun processItem(hasPermissions: Boolean)
}

class ItemProcessorImpl: ItemProcessor {
    override fun processItem(hasPermissions: Boolean) {
        println("Processing item for 3 seconds")
        sleep(3000)
    }
}

class ItemProcessorProxy(val itemProcessor: ItemProcessor): ItemProcessor {
    override fun processItem(hasPermissions: Boolean) {
        if (hasPermissions) {
            itemProcessor.processItem(hasPermissions)
        } else {
            println("Can't load")
        }
    }

}
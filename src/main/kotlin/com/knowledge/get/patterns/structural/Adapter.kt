package com.knowledge.get.patterns.structural

data class DisplayDataType(val index: Float, val data: String)

class DataDisplay {
    fun displayData(data: DisplayDataType) {
        println("Data is displayed: ${data.index} - ${data.data}")
    }
}

data class DatabaseData(val position: Int, val amount: Int)

class DatabaseDataGenerator {
    fun generateData(): List<DatabaseData> {
        val list = arrayListOf<DatabaseData>()
        list.add(DatabaseData(2, 2))
        list.add(DatabaseData(3, 7))
        list.add(DatabaseData(4, 23))
        return list
    }
}

interface DatabaseDateConverter {
    fun convertData(data: List<DatabaseData>): List<DisplayDataType>
}

class DataDisplayAdapter(val display: DataDisplay): DatabaseDateConverter {
    override fun convertData(data: List<DatabaseData>): List<DisplayDataType> {
        val returnList = arrayListOf<DisplayDataType>()
        for (datum in data) {
            val dat = DisplayDataType(datum.position.toFloat(), datum.amount.toString())
            display.displayData(dat)
        }
        return returnList
    }
}

fun main() {
    val generator = DatabaseDataGenerator()
    val generatedData = generator.generateData()
    val adapter = DataDisplayAdapter(DataDisplay())
    val convertData = adapter.convertData(generatedData)
}
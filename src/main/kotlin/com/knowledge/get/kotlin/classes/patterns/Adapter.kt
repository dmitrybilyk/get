package com.knowledge.get.kotlin.classes.patterns

fun main() {
    val regularService = RegularServiceImpl()

    val inputList = listOf("First" , "Second" , "Third" , "Fourth" , "Fifth" )

    val regularServiceAdapter = ModernServiceAdapter(regularService)
//    val output: Int = regularService.makeRequest(inputList)
    val output: String = regularServiceAdapter.makeRequest(inputList.toString())
    println(output)

    val modernService = ModernServiceImpl()
    val stringRange = 'a'..'f'
    val result = regularService.makeRequest(stringRange.map{ it.toString() })

//    so we need our code to work well with new service but we still get data from old service, new service is not implemented
//    properly yet. So, we expect here String<>String, so we need to adapt old regular service to new requirements
    val actualResult = modernService.makeRequest("Input")
}

interface RegularService {
    fun makeRequest(list: List<String>): Int
}

class RegularServiceImpl : RegularService {
    override fun makeRequest(list: List<String>): Int {
        return list.size
    }
}

class ModernServiceAdapter(private val regularService: RegularService): ModernService {
    override fun makeRequest(value: String): String {
        return regularService.makeRequest(listOf(value)).toString()
    }
}

interface ModernService {
    fun makeRequest(value: String): String
}

class ModernServiceImpl: ModernService {
    override fun makeRequest(value: String): String {
//        not implemented
        return value
    }
}

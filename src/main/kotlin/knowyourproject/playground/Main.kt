package knowyourproject.playground

fun main() {
    val myList: List<String>? = null

    println(myList.orEmpty())

    val nums = listOf(1, 2, 3)
    val moreNums = setOf(3, 4, 5)

    val result = nums.union(moreNums)

    println(result) // [1, 2, 3, 4, 5]

    val names = listOf("Alice", "Bob", "Charlie")

    val namesLength = names.associateBy { name -> name to name.length }
//    val namesIndexed = names.withIndex().associate { (index, name) -> index to name }
    println(namesLength)
//    println(namesIndexed)


//    apply → apply changes (configurator, builder)
//
//    also → also do this (logging, debug, side effects)
//
//    let → let me transform it
//
//    run → run block and return result
//
//    with → with this object, do something
}
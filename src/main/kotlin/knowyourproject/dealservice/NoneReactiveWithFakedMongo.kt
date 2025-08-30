package knowyourproject.dealservice

import java.util.concurrent.ConcurrentHashMap

data class PersonNR(val id: String, val name: String)

class BlockingRepo {
    private val db = ConcurrentHashMap<String, PersonNR>()

    fun save(person: PersonNR): PersonNR {
        Thread.sleep(100)
        db[person.id] = person
        return person
    }

    fun findById(id: String): PersonNR? {
        Thread.sleep(80)
        return db[id]
    }

    fun deleteById(id: String) {
        Thread.sleep(90)
        db.remove(id)
    }

    fun findAll(): List<PersonNR> {
        Thread.sleep(120)
        return db.values.toList()
    }
}

fun main() {
    val repo = BlockingRepo()
    val start = System.currentTimeMillis()

    for (i in 1..100) {
        repo.save(PersonNR(i.toString(), "User-$i"))
        val found = repo.findById(i.toString())
        println("Found blocking: $found")
        repo.deleteById(i.toString())
    }

    repo.findAll() // just to simulate same final call

    val duration = System.currentTimeMillis() - start
    println("Blocking test completed in $duration ms")
}

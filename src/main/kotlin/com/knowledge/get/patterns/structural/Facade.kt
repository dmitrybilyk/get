package com.knowledge.get.patterns.structural


fun main() {
    val userRepo = UserRepository()
    val user = User("john")
    userRepo.save(user)

    val retrievedUser = userRepo.findFirst()

    println(retrievedUser.login) // john
}

class ComplexSystemStore (private val filePath: String) {
    private val cache: HashMap<String, String>

    init {
        println("Reading data from the file: $filePath")
        cache = HashMap()
        // Read properties from file and put to cache
    }

    fun store(key: String, value: String) {
        cache[key] = value
    }

    fun read(key: String) = cache[key] ?: ""

    fun commit() = println("Storing cached data to file $filePath")
}

data class User(val login: String)


// Facade
class UserRepository {
    private val systemPreferences = ComplexSystemStore("/data/default.prefs")

    fun save(user: User) {
        systemPreferences.store("USER_KEY", user.login)
        systemPreferences.commit()
    }

    fun findFirst(): User = User(systemPreferences.read("USER_KEY"))
}
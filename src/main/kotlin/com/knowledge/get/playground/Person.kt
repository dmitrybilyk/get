import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.lang.Thread.sleep

data class Person(val name: String, val city: String)
data class Firm(val name: String, val owner: String)
data class Car(val name: String, val owner: String)
data class PersonSummary(val name: String, val carBrand: String, val firmName: String)

// Mock DAO classes
class PersonDao {
    fun getPersons(): List<Person> {
        println("[DAO] Fetching persons on thread: ${Thread.currentThread().name}")
        sleep(500) // simulate delay
        return listOf(
            Person("person1", "Lviv"),
            Person("person2", "Lviv"),
            Person("person3", "Lviv")
        )
    }
}

class FirmDao {
    fun getFirms(): List<Firm> {
        println("[DAO] Fetching firms on thread: ${Thread.currentThread().name}")
        sleep(1000)
        return listOf(
            Firm("firm1", "person1"),
            Firm("firm2", "person2"),
            Firm("firm3", "person3")
        )
    }
}

class CarDao {
    fun getCars(): List<Car> {
        println("[DAO] Fetching cars on thread: ${Thread.currentThread().name}")
        sleep(1000)
        return listOf(
            Car("car1", "person1"),
            Car("car2", "person2"),
            Car("car3", "person3")
        )
    }
}

fun main() {
    val personDao = PersonDao()
    val firmDao = FirmDao()
    val carDao = CarDao()

    val result = Mono.zip(
        Mono.fromCallable {
            personDao.getPersons()
        }
            .doOnSubscribe { println("[PERSONS] Subscribed to personDao on thread: ${Thread.currentThread().name}") }
            .subscribeOn(Schedulers.parallel()),

        Mono.fromCallable { firmDao.getFirms() }
            .subscribeOn(Schedulers.parallel())
            .flatMapMany { firms -> Flux.fromIterable(firms) }
            .doOnSubscribe { println("[FIRMS] Subscribed to firmDao on thread: ${Thread.currentThread().name}") }
            .doOnNext { println("[FIRMS] Processing firm: $it on thread: ${Thread.currentThread().name}") }
            .collectMap { it.owner },

        Mono.fromCallable { carDao.getCars() }
            .subscribeOn(Schedulers.parallel())
            .flatMapMany { cars -> Flux.fromIterable(cars)}
            .doOnSubscribe { println("[CARS] Subscribed to carDao on thread: ${Thread.currentThread().name}") }
            .doOnNext { println("[CARS] Processing car: $it on thread: ${Thread.currentThread().name}") }
            .collectMap { it.owner }
    )
        .flatMapMany { tuple ->
            val persons = tuple.t1
            val firmMap = tuple.t2
            val carMap = tuple.t3
            println("[ZIP] Got persons, firmMap, carMap on thread: ${Thread.currentThread().name}")

            Flux.fromIterable(persons)
                .doOnSubscribe { println("[SUMMARY] Starting summary processing on thread: ${Thread.currentThread().name}") }
                .subscribeOn(Schedulers.parallel())
                .map { person ->
                    println("[SUMMARY] Creating summary for ${person.name} on thread: ${Thread.currentThread().name}")
                    PersonSummary(
                        person.name,
                        carMap[person.name]!!.name,
                        firmMap[person.name]!!.name
                    )
                }
        }

    result.subscribe { summary ->
        println("[RESULT] $summary on thread: ${Thread.currentThread().name}")
    }

    sleep(5000) // Wait to let everything finish
}

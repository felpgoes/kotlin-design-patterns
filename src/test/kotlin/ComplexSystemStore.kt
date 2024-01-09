import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ComplexSystemStore (private val filePath: String) {
    private val cache: HashMap<String, String>

    init {
        println("Reading data from the fila: $filePath")
        cache = HashMap()
        // Le arquivo e coloca no cache aqui
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
    private val systemPreferences = ComplexSystemStore("./data/default.prefs")

    fun save(user: User) {
        systemPreferences.store("USER_KEY", user.login)
        systemPreferences.commit()
    }

    fun findFirst(): User = User(systemPreferences.read("USER_KEY"))
}

class FacadeTest {
    @Test
    fun testFacade() {
        val userRepo = UserRepository()
        val user = User("Felipe")
        userRepo.save(user)
        val retrievedUser = userRepo.findFirst()

        Assertions.assertEquals(retrievedUser.login, "Felipe")
    }
}
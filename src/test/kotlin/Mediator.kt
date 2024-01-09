import org.junit.jupiter.api.Test

class ChatUser (private val mediator: Mediator, private val name: String) {
    fun send(message: String) {
        println("[SEND] $name: $message")
        mediator.sendMessage(message, this)
    }

    fun receive(message: String) {
        println("[RECEIVE] $name: $message")
    }
}

class Mediator {
    private val users = arrayListOf<ChatUser>()

    fun sendMessage(message: String, user: ChatUser) {
        users.filterNot { it == user }.forEach { it.receive(message) }
    }

    fun addUser (user: ChatUser): Mediator = apply { users.add(user) }
}

class MediatorTest {
    @Test
    fun testMediator() {
        val mediator = Mediator()
        val alice = ChatUser(mediator, "Alice")
        val bob = ChatUser(mediator, "Bob")
        val carol = ChatUser(mediator, "Carol")

        mediator
            .addUser(alice)
            .addUser(bob)
            .addUser(carol)

        carol.send("Hello!!")
    }
}
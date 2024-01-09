import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

data class Memento(val state: String)

class Originator(var state: String) {
    fun createMemento() = Memento(state)
    fun restoreMemento(memento: Memento) = apply { state = memento.state }
}

class CareTaker {
    private val mementoList = arrayListOf<Memento>()

    fun save(state: Memento) = mementoList.add(state)
    fun restore(index: Int) = mementoList[index]
}

class MementoTest {
    @Test
    fun testMemento() {
        val originator = Originator("Initial State")
        val careTaker = CareTaker()
        careTaker.save(originator.createMemento())

        originator.state = "State 1"
        careTaker.save(originator.createMemento())

        originator.state = "State 2"
        careTaker.save(originator.createMemento())

        println("Current state: ${originator.state}")

        Assertions.assertEquals(originator.state, "State 2") // Ultimo estado

        originator.restoreMemento(careTaker.restore(1))
        Assertions.assertEquals(originator.state, "State 1") // Voltando para o segundo estado

        originator.restoreMemento(careTaker.restore(2))
        Assertions.assertEquals(originator.state, "State 2") // Voltando para o ultimo estado
    }
}
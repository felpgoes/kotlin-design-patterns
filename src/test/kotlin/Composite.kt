import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

open class Equipment(open val price: Int, val name: String)

open class Composite(name: String): Equipment(0, name) {
    private val equipments = ArrayList<Equipment>()

    override val price: Int
        get() = equipments.sumOf { it.price }

    fun add(equipment: Equipment) = apply { equipments.add(equipment) }
}

class Computer: Composite("PC")
class Processor: Equipment(1000, "Processor")
class HardDrive: Equipment(250, "Hard Drive")
class Memory: Composite("Memory")
class ROM: Equipment(100, "Read Only Memory")
class RAM: Equipment(75, "Random Access Memory")

class CompositeTest {
    @Test
    fun testComposite() {
        val memory = Memory()
            .add(ROM())
            .add(RAM())

        val computer = Computer()
            .add(memory)
            .add(Processor())
            .add(HardDrive())

        println("PC price: ${computer.price}")

        Assertions.assertEquals(computer.name, "PC")
        Assertions.assertEquals(computer.price, 1425)
    }
}


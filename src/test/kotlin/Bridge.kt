import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

interface Device {
    var volume: Int
    fun getName(): String
}
class Radio: Device {
    override var volume: Int = 0
    override fun getName() = "Radio $this"
}
class TV: Device {
    override var volume: Int = 0
    override fun getName() = "TV $this"
}

interface Remote {
    fun volumeUp()
    fun volumeDown()
}
class BasicRemote(val device: Device): Remote{
    override fun volumeUp() {
        device.volume++
        println("${device.getName()} volume up to ${device.volume}")
    }

    override fun volumeDown() {
        device.volume--
        println("${device.getName()} volume down to ${device.volume}")
    }
}

class BridgeTest {
    @Test
    fun testBridge() {
        val tv = TV()
        val radio = Radio()

        val tvRemote = BasicRemote(tv)
        val radioRemote = BasicRemote(radio)

        tvRemote.volumeUp()
        tvRemote.volumeUp()
        tvRemote.volumeDown()

        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeDown()

        Assertions.assertEquals(tv.volume, 1)
        Assertions.assertEquals(radio.volume, 2)
    }
}
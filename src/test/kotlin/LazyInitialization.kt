import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AlertBox {
    var message: String? = null

    init { println("Initializing $this") }

    fun show() { println("AlertBox $this: $message") }
}

class Window {
    val box by lazy { AlertBox() }

    init { println("Initializing $this")     }

    fun showMessage(message: String) {
        box.message = message
        box.show()
    }
}

class Window2 {
    lateinit var box: AlertBox

    fun showMessage(message: String) {
        box = AlertBox()
        box.message = message
        box.show()
    }
}

class WindowTest () {
    @Test
    fun windowTest () {
        val window = Window()
        println("After creating Window")
        window.showMessage("Hello there!")

        Assertions.assertNotNull(window.box)

        val window2 = Window2()
        println("After creating Window2")
        window2.showMessage("Hello there2!")

        Assertions.assertNotNull(window2.box)
    }
}
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Printer(private val stringFormatterStrategy: (String) -> String) {
    fun printString(message: String): String {
        val formattedString = stringFormatterStrategy(message)
        println(formattedString)
        return formattedString
    }
}

val lowercaseFormatter = { it: String -> it.lowercase() }
val uppercaseFormatter = { it: String -> it.uppercase() }

object PrinterStrategy {
    fun formatter (value: String, field: String): String {
        if("name" == field) return uppercaseFormatter(value)
        if("address" == field) return uppercaseFormatter("Rua $value")

        return lowercaseFormatter(value)
    }
}

class StrategyTest {
    @Test
    fun testStrategy() {
        val inputString = "FELIPE goes TesTe LEGal"

        val lowercasePrinterStrategy = PrinterStrategy.formatter(inputString, "name")
        val uppercasePrinterStrategy = PrinterStrategy.formatter(inputString, "test")

        println("lowercasePrinterStrategy: $lowercasePrinterStrategy\nuppercasePrinterStrategy: $uppercasePrinterStrategy")

        val lowercasePrinter = Printer(lowercaseFormatter).printString(inputString)
        val uppercasePrinter = Printer(uppercaseFormatter).printString(inputString)

        Assertions.assertEquals(lowercasePrinter, "felipe goes teste legal")
        Assertions.assertEquals(uppercasePrinter, "FELIPE GOES TESTE LEGAL")
    }
}


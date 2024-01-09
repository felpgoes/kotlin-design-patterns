import org.junit.jupiter.api.Test

interface CoffeeMachine {
    fun makeSmallCoffee()
    fun makeLargeCoffee()
}

class NormalCoffeeMachine: CoffeeMachine {
    override fun makeSmallCoffee() {
        println("Normal coffee machine: Making small coffee")
    }

    override fun makeLargeCoffee() {
        println("Normal coffee machine: Making large coffee")
    }
}

// Decorator
class EnhancedCoffeeMachine(private val coffeeMachine: CoffeeMachine): CoffeeMachine by coffeeMachine {
    // CoffeeMachine by coffeeMachine = Implementa a interface utilizando a classe recebida por parametro

    // Overriding behavior - Sobrescrevendo o comportamento da classe passada por parametro
    override fun makeLargeCoffee() {
        println("Enhanced coffee machine: Making large coffee")
    }

    // Extending behavior - Sobrescrevendo o comportamento da classe passada por parametro
    fun makeMilkCoffee(){
        coffeeMachine.makeSmallCoffee()
        // Adicionando milk aqui
        println("Enhanced coffee machine: Making mild coffee")
    }
}

class DecoratorTest {
    @Test
    fun testDecorator() {
        val normalMachine = NormalCoffeeMachine()
        val enhancedMachine = EnhancedCoffeeMachine(normalMachine)

        enhancedMachine.makeSmallCoffee() // Usa o small do NormalCoffeeMachine
        enhancedMachine.makeLargeCoffee() // Usa o large da extensão EnhancedCoffeeMachine
        enhancedMachine.makeMilkCoffee() // Usa o small do NormalCoffeeMachine e a extensão do EnhancedCoffeeMachine
    }
}
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

abstract class Shape: Cloneable {
    var type: String? = null

    abstract fun draw()

    public override fun clone(): Any {
        var clone: Any? = null
        try {
            clone = super.clone()
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        return clone!!
    }
}

class Rectangle: Shape() {
    override fun draw() {
        println("Inside Rectangle::draw() method.")
    }

    init {
        type = "Rectangle"
    }
}

class Square: Shape() {
    override fun draw() {
        println("Inside Square::draw() method.")
    }

    init {
        type = "Square"
    }
}

class Circle: Shape() {
    override fun draw() {
        println("Inside Circle::draw() method.")
    }

    init {
        type = "Circle"
    }
}

object ShapeCache {
    private val shapeMap = hashMapOf<String?, Shape>()

    fun loadCache() {
        val circle = Circle()
        val square = Square()
        val rectangle = Rectangle()

        shapeMap["1"] = circle
        shapeMap["2"] = square
        shapeMap["3"] = rectangle
    }

    fun getShape(shapeId: String): Shape {
        val cachedShape = shapeMap[shapeId]
        return cachedShape?.clone() as Shape
    }
}

class PrototypeTest {
    @Test
    fun testPrototype() {
        ShapeCache.loadCache()
        val clonedShape1_1 = ShapeCache.getShape("1")
        val clonedShape1_2 = ShapeCache.getShape("1")
        val clonedShape1_3 = ShapeCache.getShape("1")
        val clonedShape2 = ShapeCache.getShape("2")
        val clonedShape3 = ShapeCache.getShape("3")

        println("clonedShape1_1: $clonedShape1_1")
        println("clonedShape1_2: $clonedShape1_2")
        println("clonedShape1_3: $clonedShape1_3")

        Assertions.assertEquals(clonedShape1_1.type, "Circle")
        Assertions.assertEquals(clonedShape2.type, "Square")
        Assertions.assertEquals(clonedShape3.type, "Rectangle")
    }
}
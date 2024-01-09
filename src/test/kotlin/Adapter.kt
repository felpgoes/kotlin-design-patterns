import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

// ---- START of 3rd party functionality
data class DisplayDataType(val index: Float, val date: String)

class DataDisplay {
    fun displayData(data: DisplayDataType) {
        println("Data is displayed: ${data.index} - ${data.date}")
    }
}
// ------------------------------------ END
data class DatabaseData(val position: Int, val amount: Int)

class DatabaseDataGenerator{
    fun generateData(): List<DatabaseData> {
        val list = arrayListOf<DatabaseData>()
        list.add(DatabaseData(2,2))
        list.add(DatabaseData(3,7))
        list.add(DatabaseData(4,23))
        return list
    }
}

interface DatabaseDataConverter {
    fun convertData(data: List<DatabaseData>): List<DisplayDataType>
}

class DataDisplayAdapter(val display: DataDisplay): DatabaseDataConverter {
    override fun convertData(data: List<DatabaseData>): List<DisplayDataType> {
//        Implementação do curso
//        val returnList = arrayListOf<DisplayDataType>()
//        for (datum in data) {
//            val ddt = DisplayDataType(datum.position.toFloat(), datum.amount.toString())
//            display.displayData(ddt)
//            returnList.add(ddt)
//        }
//        return returnList

        return data.map { it ->
            val ddt = DisplayDataType(it.position.toFloat(), it.amount.toString())
            display.displayData(ddt)
            ddt
        }
    }
}

class AdapterTest {
    @Test
    fun adapterTest() {
        val generator = DatabaseDataGenerator()
        val generatedData = generator.generateData()
        val adapter = DataDisplayAdapter(DataDisplay())
        val convertedData = adapter.convertData(generatedData)

        Assertions.assertEquals(convertedData.size, 3)
        Assertions.assertEquals(convertedData[1].index, 3f)
        Assertions.assertEquals(convertedData[1].date,"7")
    }
}
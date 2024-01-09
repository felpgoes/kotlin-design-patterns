import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

interface DataSource
class DatabaseDataSource: DataSource
class NetworkDataSource: DataSource
abstract class DataSourceFactory {
    abstract fun makeDateSource(): DataSource
    companion object {
        inline fun <reified T: DataSource> createFactory(): DataSourceFactory =
            when(T::class) {
                DatabaseDataSource::class -> DatabaseFactory()
                NetworkDataSource::class -> NetworkFactory()
                else -> throw IllegalArgumentException()
            }
    }
}
class NetworkFactory: DataSourceFactory() { override fun makeDateSource(): DataSource = NetworkDataSource() }
class DatabaseFactory: DataSourceFactory() { override fun makeDateSource(): DataSource = DatabaseDataSource() }

class AbstractFactoryTest {
    @Test
    fun aftest() {
        val datasourceFactory = DataSourceFactory.createFactory<DatabaseDataSource>()
        val dataSource = datasourceFactory.makeDateSource()
        println("Created datasource $dataSource")

        Assertions.assertInstanceOf(DatabaseDataSource::class.java, dataSource)
    }
}
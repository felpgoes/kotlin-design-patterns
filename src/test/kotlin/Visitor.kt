import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

interface ReportElement {
    fun <R> accept(visitor: ReportVisitor<R>): R
}

class FixedPriceContract(val costPerYear: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class  TimeAndMaterialsContract(val costPerHour: Long, val hours: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class SupportContract(val costPerMonth: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

interface ReportVisitor <out R> {
    fun visit(contract: FixedPriceContract): R
    fun visit(contract: TimeAndMaterialsContract): R
    fun visit(contract: SupportContract): R
}

class MonthlyCostReportVisitor: ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear / 12
    override fun visit(contract: TimeAndMaterialsContract): Long = contract.costPerHour * contract.hours
    override fun visit(contract: SupportContract): Long = contract.costPerMonth
}

class YearlyCostReportVisitor: ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear
    override fun visit(contract: TimeAndMaterialsContract): Long = contract.costPerHour * contract.hours
    override fun visit(contract: SupportContract): Long = contract.costPerMonth * 12
}

class VisitorTest {
    @Test
    fun testVisitor() {
        val projectAlpha = FixedPriceContract(10_000)
        val projectBeta = SupportContract(500)
        val projectCharlie = TimeAndMaterialsContract(150, 10)
        val projectDelta = TimeAndMaterialsContract(50, 50)

        val projects = arrayListOf(projectAlpha, projectBeta, projectCharlie, projectDelta)

        val monthlyCostVisitor = MonthlyCostReportVisitor()
        val monthlyCost = projects.sumOf { it.accept(monthlyCostVisitor) }
        println("Custo mensal: $monthlyCost")
        Assertions.assertEquals(monthlyCost, 5333)

        val yearlyCostReportVisitor = YearlyCostReportVisitor()
        val yearlyCost = projects.sumOf { it.accept(yearlyCostReportVisitor) }
        println("Custo anual: $yearlyCost")
        Assertions.assertEquals(yearlyCost, 20_000)

    }
}
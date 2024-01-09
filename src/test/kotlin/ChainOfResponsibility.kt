import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

interface HandlerChain {
    fun addHandler(inputHeader: String): String
}

class AuthenticationHeader(private val token: String?, var next: HandlerChain? = null): HandlerChain {
    override fun addHandler(inputHeader: String): String =
        "$inputHeader\nAuthorization: $token"
            .let{ next?.addHandler(it) ?: it }
}

class ContentTypeHeader(private val contentType: String, var next: HandlerChain? = null): HandlerChain {
    override fun addHandler(inputHeader: String): String =
        "$inputHeader\nContentType: $contentType"
            .let{ next?.addHandler(it) ?: it }
}

class BodyPayloadHeader(private val body: String, var next: HandlerChain? = null): HandlerChain {
    override fun addHandler(inputHeader: String): String =
        "$inputHeader\n$body"
            .let{ next?.addHandler(it) ?: it }
}

class ChainOfResponsibilityTest {
    @Test
    fun testChainOfResponsibility() {
        val authHeader = AuthenticationHeader("F3L1P3")
        val contentTypeHeader = ContentTypeHeader("json")
        val bodyHeader = BodyPayloadHeader("Body: {\n\"username\": \"Felipe\"}")

        authHeader.next = contentTypeHeader
        contentTypeHeader.next = bodyHeader

        val msgWithAuth = authHeader.addHandler("Headers with auth")
        println(msgWithAuth)
        println("-----------------------")
        val msgWithoutAuth = contentTypeHeader.addHandler("Headers without auth")
        println(msgWithoutAuth)

        Assertions.assertEquals(msgWithAuth,
            "Headers with auth\n" +
                "Authorization: F3L1P3\n" +
                "ContentType: json\n" +
                "Body: {\n" +
                "\"username\": \"Felipe\"}")
        Assertions.assertEquals(msgWithoutAuth,
            "Headers without auth\n" +
                "ContentType: json\n" +
                "Body: {\n" +
                "\"username\": \"Felipe\"}")
    }
}
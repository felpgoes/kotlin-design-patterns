import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

sealed class AuthorizationState

data object Unauthorized: AuthorizationState()

class Authorized(val username: String): AuthorizationState()

class AuthorizationPresenter {
    private var state: AuthorizationState = Unauthorized

    val isAuthorized: Boolean
        get() = when(state) {
            is Authorized -> true
            is Unauthorized -> false
        }

    val username: String
        get() = when(state) {
            is Authorized -> (state as Authorized).username
            is Unauthorized -> "Unknown"
        }

    fun loginUser(username: String) {
        state = Authorized(username)
    }

    fun logoutUser() {
        state = Unauthorized
    }

    override fun toString() = "User $username is logged in: $isAuthorized"
}

class StateTest {
    @Test
    fun testState() {
        val authorizationPresenter = AuthorizationPresenter()
        authorizationPresenter.loginUser("Felipe")
        println(authorizationPresenter)

        Assertions.assertTrue(authorizationPresenter.isAuthorized)
        Assertions.assertEquals(authorizationPresenter.username, "Felipe")

        authorizationPresenter.logoutUser()
        println(authorizationPresenter)

        Assertions.assertFalse(authorizationPresenter.isAuthorized)
        Assertions.assertEquals(authorizationPresenter.username, "Unknown")
    }
}
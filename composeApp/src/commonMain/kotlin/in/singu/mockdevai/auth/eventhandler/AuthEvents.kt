package `in`.singu.mockdevai.auth.eventhandler

sealed class AuthEvents(
    message: String? = null,
    error: String? = null
) {

    object AuthSuccess : AuthEvents()
    data class AuthFailed(val error: String) : AuthEvents(error = error)

}
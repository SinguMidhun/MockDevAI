package `in`.singu.mockdevai.auth.eventhandler

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import `in`.singu.mockdevai.auth.routes.OnBoarding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AuthEventsImpl(
    private val navController: NavController
) {

    fun handle(authEvents: AuthEvents) {
        when (authEvents) {
            is AuthEvents.AuthSuccess -> {
                navController.navigate(OnBoarding)
            }

            is AuthEvents.AuthFailed -> {
                CoroutineScope(Dispatchers.Main).launch {

                }
            }
        }
    }

}
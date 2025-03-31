package `in`.singu.mockdevai.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import `in`.singu.mockdevai.auth.AuthScreen
import `in`.singu.mockdevai.auth.eventhandler.AuthEventsImpl
import `in`.singu.mockdevai.auth.routes.Auth
import `in`.singu.mockdevai.auth.routes.CreateProfile

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(startDestination = CreateProfile, route = Auth::class) {
        val authEvents = AuthEventsImpl(navController)
        composable<CreateProfile> {
            AuthScreen {
                authEvents.handle(it)
            }
        }

    }
}
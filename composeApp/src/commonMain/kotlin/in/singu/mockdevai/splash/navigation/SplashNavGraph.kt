package `in`.singu.mockdevai.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import `in`.singu.mockdevai.splash.routes.Splash
import `in`.singu.mockdevai.splash.SplashScreen
import `in`.singu.mockdevai.splash.eventhandler.SplashEventHandlerImpl
import `in`.singu.mockdevai.splash.routes.SplashStart

fun NavGraphBuilder.splashNavigation(navController : NavController){

    navigation(startDestination = Splash, route = SplashStart::class){

        composable<Splash> {
            val eventHandler = SplashEventHandlerImpl(navController)
            SplashScreen { splashEvent ->
                eventHandler.handle(splashEvent)
            }
        }

    }

}
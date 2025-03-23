package `in`.singu.mockdevai

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import `in`.singu.mockdevai.onboarding.navigation.onBoardingNavGraph
import `in`.singu.mockdevai.splash.routes.OnBoarding
import `in`.singu.mockdevai.splash.routes.Splash
import `in`.singu.mockdevai.splash.SplashScreen
import `in`.singu.mockdevai.splash.eventhandler.SplashEventHandlerImpl
import `in`.singu.mockdevai.splash.navigation.splashNavigation
import `in`.singu.mockdevai.ui.LightColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(colorScheme = LightColors) {

        val navController = rememberNavController()

        NavHost(
            navController, startDestination = OnBoarding,
            enterTransition = {
                fadeIn(animationSpec = tween(700)) +
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            tween(700)
                        )
            }, exitTransition = {
                fadeOut(animationSpec = tween(700)) +
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            tween(700)
                        )
            }, popEnterTransition = {
                fadeIn(animationSpec = tween(700)) +
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            tween(700)
                        )
            }, popExitTransition = {
                fadeOut(animationSpec = tween(700)) +
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            tween(700)
                        )
            }
        ) {

            splashNavigation(navController)

            onBoardingNavGraph(navController)

        }

    }
}
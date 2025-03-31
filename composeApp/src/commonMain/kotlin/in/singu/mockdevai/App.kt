package `in`.singu.mockdevai

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import `in`.singu.mockdevai.auth.navigation.authNavGraph
import `in`.singu.mockdevai.auth.routes.Auth
import `in`.singu.mockdevai.onboarding.navigation.onBoardingNavGraph
import `in`.singu.mockdevai.splash.navigation.splashNavigation
import `in`.singu.mockdevai.splash.routes.SplashStart
import `in`.singu.mockdevai.ui.LightColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    LaunchedEffect(Unit) {
        GoogleAuthProvider.create(credentials = GoogleAuthCredentials(serverId = "1028009092029-7fq30fqjo29jikqj1ojpr77jhadkl9nq.apps.googleusercontent.com"))
//         - ios 1028009092029-7fq30fqjo29jikqj1ojpr77jhadkl9nq.apps.googleusercontent.com
        // android - 1028009092029-f2sjilqk68mjp0aptn482dpg30er69n3.apps.googleusercontent.com
    }

    MaterialTheme(colorScheme = LightColors) {

        val navController = rememberNavController()

        NavHost(
            navController, startDestination = SplashStart,
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
            authNavGraph(navController)
            onBoardingNavGraph(navController)

        }

    }
}
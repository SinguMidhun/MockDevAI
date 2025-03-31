package `in`.singu.mockdevai.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import `in`.singu.mockdevai.auth.routes.OnBoarding
import `in`.singu.mockdevai.onboarding.SurveyScreen
import `in`.singu.mockdevai.onboarding.eventhandler.OnBoardingEventsImpl
import `in`.singu.mockdevai.onboarding.routes.Question1

fun NavGraphBuilder.onBoardingNavGraph(navController: NavController) {
    navigation(startDestination = Question1, route = OnBoarding::class) {
        composable<Question1> {
            val eventHandler = OnBoardingEventsImpl(navController)
            SurveyScreen(
                onEvent = { event -> eventHandler.handle(event) }
            )
        }
    }
}
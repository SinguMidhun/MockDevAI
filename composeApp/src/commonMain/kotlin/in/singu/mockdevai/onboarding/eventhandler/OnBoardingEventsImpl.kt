package `in`.singu.mockdevai.onboarding.eventhandler

import androidx.navigation.NavController

class OnBoardingEventsImpl(
    private val navController: NavController,
) {
    fun handle(event: OnBoardingEvents) {
        when (event) {
            is OnBoardingEvents.navigateUp -> {
                navController.navigateUp()
            }
            is OnBoardingEvents.onboardingFinished -> {
                navController.navigateUp()
            }
        }
    }

}
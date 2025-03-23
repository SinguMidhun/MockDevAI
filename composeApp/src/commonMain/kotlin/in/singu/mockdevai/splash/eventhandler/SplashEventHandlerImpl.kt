package `in`.singu.mockdevai.splash.eventhandler

import androidx.navigation.NavController
import `in`.singu.mockdevai.onboarding.routes.OnBoardingRoutes


class SplashEventHandlerImpl(private val navController: NavController) {

    fun handle(event : SplashEvent){
        when(event) {
            is SplashEvent.onVerificationSuccess -> {
                navController.navigate(OnBoardingRoutes)
            }

            is SplashEvent.onVerificationFailed -> {
                navController.navigate(OnBoardingRoutes)
            }
        }
    }

}
package `in`.singu.mockdevai.splash.eventhandler

import androidx.navigation.NavController
import `in`.singu.mockdevai.auth.routes.Auth


class SplashEventHandlerImpl(private val navController: NavController) {

    fun handle(event : SplashEvent){
        when(event) {
            is SplashEvent.onVerificationSuccess -> {
                navController.navigate(Auth)
            }

            is SplashEvent.onVerificationFailed -> {
                navController.navigate(Auth)
            }

            SplashEvent.authPending -> {
                navController.navigate(Auth)
            }
            SplashEvent.onBoardingPending -> {
                navController.navigate(Auth)
            }
        }
    }

}
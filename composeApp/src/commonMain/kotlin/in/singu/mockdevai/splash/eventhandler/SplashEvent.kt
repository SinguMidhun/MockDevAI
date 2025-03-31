package `in`.singu.mockdevai.splash.eventhandler

sealed class SplashEvent() {
    data object onVerificationSuccess : SplashEvent()
    data object authPending : SplashEvent()
    data object onBoardingPending : SplashEvent()
    data class onVerificationFailed(val exception: Exception) : SplashEvent()
}

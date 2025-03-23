package `in`.singu.mockdevai.splash.eventhandler

sealed class SplashEvent() {
    data object onVerificationSuccess : SplashEvent()
    data class onVerificationFailed(val exception: Exception) : SplashEvent()
}

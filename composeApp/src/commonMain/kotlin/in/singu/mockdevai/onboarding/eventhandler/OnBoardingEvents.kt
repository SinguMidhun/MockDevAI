package `in`.singu.mockdevai.onboarding.eventhandler

sealed class OnBoardingEvents {
    object navigateUp : OnBoardingEvents()
    object onboardingFinished : OnBoardingEvents()
}
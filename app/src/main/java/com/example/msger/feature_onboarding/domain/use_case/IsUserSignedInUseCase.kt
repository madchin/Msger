package com.example.msger.feature_onboarding.domain.use_case

import com.example.msger.feature_onboarding.domain.repository.AuthOnboardingRepository

class IsUserSignedInUseCase(
    private val authOnboardingRepository: AuthOnboardingRepository
) {
    operator fun invoke() = authOnboardingRepository.isSignedIn
}
package com.example.msger.feature_onboarding.domain.use_case

import com.example.msger.feature_onboarding.domain.repository.AuthOnboardingRepository


class IsUserSignedInUseCase(
    private val authRepository: AuthOnboardingRepository
) {
    operator fun invoke() = authRepository.isSignedIn
}
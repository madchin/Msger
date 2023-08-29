package com.example.msger.feature_onboarding.domain.use_case

import com.example.msger.feature_onboarding.data.repository.AuthOnboardingRepositoryImpl
import com.example.msger.feature_onboarding.domain.repository.AuthOnboardingRepository

class OnboardingUseCasesWrapper(
    authRepository: AuthOnboardingRepository = AuthOnboardingRepositoryImpl()
) {
    val isUserSignedInUseCase = IsUserSignedInUseCase(authRepository = authRepository)
}
package com.example.msger.feature_onboarding.data.repository

import com.example.msger.feature_onboarding.data.data_source.AuthenticatorOnboarding
import com.example.msger.feature_onboarding.data.data_source.AuthenticatorOnboardingImpl
import com.example.msger.feature_onboarding.domain.repository.AuthOnboardingRepository


class AuthOnboardingRepositoryImpl(
    private val auth: AuthenticatorOnboarding = AuthenticatorOnboardingImpl()
) : AuthOnboardingRepository {

    override val isSignedIn: Boolean
        get() = auth.isSignedIn
}
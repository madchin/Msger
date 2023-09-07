package com.example.msger.feature_onboarding.data.repository

import com.example.msger.core.data.data_source.remote.auth.Auth
import com.example.msger.core.data.data_source.remote.auth.AuthImpl
import com.example.msger.feature_onboarding.domain.repository.AuthOnboardingRepository


class AuthOnboardingRepositoryImpl(
    private val auth: Auth = AuthImpl()
) : AuthOnboardingRepository {

    override val isSignedIn: Boolean
        get() = auth.isSignedIn
}
package com.example.msger.feature_authentication.domain.use_case

import com.example.msger.core.util.ApiResponse
import com.example.msger.core.util.config.toApiResponse
import com.example.msger.feature_authentication.domain.model.UserDto
import com.example.msger.feature_authentication.domain.service.AuthService
import retrofit2.Response

class SignUpUseCase(
    private val authService: AuthService
) {
    suspend operator fun invoke(user: UserDto): ApiResponse<UserDto> =
        try {
            authService.signUp(user).let(Response<UserDto>::toApiResponse)
        } catch (e: Exception) {
            ApiResponse.Error()
        }
}
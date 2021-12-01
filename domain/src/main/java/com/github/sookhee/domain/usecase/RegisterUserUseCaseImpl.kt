package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.UserRepository
import com.github.sookhee.domain.entity.User
import javax.inject.Inject

class RegisterUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): RegisterUserUseCase {
    override suspend fun invoke(user: User): Boolean {
        return userRepository.registerUser(user)
    }
}
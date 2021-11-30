package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.UserRepository
import com.github.sookhee.domain.entity.User
import javax.inject.Inject

class GetUserInfoUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUserInfoUseCase {
    override suspend fun invoke(employeeNo: String): User {
        return userRepository.getUserInfo(employeeNo)
    }
}
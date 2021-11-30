package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.entity.User

interface GetUserInfoUseCase {
    suspend operator fun invoke(employeeNo: String): User
}
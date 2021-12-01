package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.entity.User

interface RegisterUserUseCase {
    suspend operator fun invoke(user: User): Boolean
}
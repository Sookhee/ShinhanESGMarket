package com.github.sookhee.domain

import com.github.sookhee.domain.entity.User

interface UserRepository {
    suspend fun getUserInfo(employeeNo: String): User

    suspend fun registerUser(user: User): Boolean
}
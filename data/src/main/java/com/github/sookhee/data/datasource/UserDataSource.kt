package com.github.sookhee.data.datasource

import com.github.sookhee.domain.entity.User

interface UserDataSource {
    suspend fun getUserInfo(employeeNum: String): User

    suspend fun registerUser(user: User): Boolean
}
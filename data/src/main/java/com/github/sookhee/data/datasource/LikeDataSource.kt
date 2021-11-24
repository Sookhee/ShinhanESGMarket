package com.github.sookhee.data.datasource

import com.github.sookhee.domain.entity.Like

interface LikeDataSource {
    suspend fun getUserLike(userId: String): List<Like>
}
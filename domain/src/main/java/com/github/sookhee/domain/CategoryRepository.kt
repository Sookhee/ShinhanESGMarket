package com.github.sookhee.domain

import com.github.sookhee.domain.entity.Category

interface CategoryRepository {
    suspend fun getCategoryList(): List<Category>
}
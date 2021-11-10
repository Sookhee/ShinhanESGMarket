package com.github.sookhee.domain

import com.github.sookhee.domain.entity.Category

interface CategoryRepository {
    fun getCategoryList(): List<Category>
}
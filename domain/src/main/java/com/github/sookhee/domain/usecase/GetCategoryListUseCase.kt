package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.entity.Category

interface GetCategoryListUseCase {
    suspend operator fun invoke(): List<Category>
}
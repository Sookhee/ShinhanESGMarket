package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.CategoryRepository
import com.github.sookhee.domain.entity.Category
import javax.inject.Inject

class GetCategoryListUseCaseImpl @Inject constructor(private val repository: CategoryRepository) :
    GetCategoryListUseCase {
    override fun invoke(): List<Category> {
        val result = repository.getCategoryList()
        return listOf()
    }
}
package com.github.sookhee.data

import com.github.sookhee.data.datasource.CategoryDataSource
import com.github.sookhee.domain.CategoryRepository
import com.github.sookhee.domain.entity.Category
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val dataSource: CategoryDataSource) :
    CategoryRepository {
    override fun getCategoryList(): List<Category> {
        val result = dataSource.getCategoryList()

        return listOf()
    }
}
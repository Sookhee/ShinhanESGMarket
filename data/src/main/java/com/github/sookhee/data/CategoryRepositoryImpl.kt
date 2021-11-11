package com.github.sookhee.data

import com.github.sookhee.data.datasource.CategoryDataSource
import com.github.sookhee.domain.CategoryRepository
import com.github.sookhee.domain.entity.Category
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val dataSource: CategoryDataSource) :
    CategoryRepository {
    override suspend fun getCategoryList(): List<Category> {
        val result = dataSource.getCategoryList()
        val categoryList = mutableListOf<Category>()

        result.forEach{
            categoryList.add(
                Category(
                    it.id,
                    it.name,
                    it.iconUrl
                )
            )
        }

        return categoryList
    }
}
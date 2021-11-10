package com.github.sookhee.data.datasource

import com.github.sookhee.data.spec.CategoryResponse

interface CategoryDataSource {
    fun getCategoryList(): List<CategoryResponse>
}
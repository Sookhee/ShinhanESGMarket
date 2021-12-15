package com.github.sookhee.data.datasource

import com.github.sookhee.data.spec.CategoryResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor() : CategoryDataSource {
    override suspend fun getCategoryList(): List<CategoryResponse> {
        val categoryList = mutableListOf<CategoryResponse>()
        val resultList = FirebaseFirestore.getInstance()
            .collection(COLLECTION)
            .orderBy(KEY_ID)
            .get().await()

        for (document in resultList) {
            val id = document.id
            val name = document.getString(KEY_NAME) ?: ""
            val icon = document.getString(KEY_ICON) ?: ""
            categoryList.add(CategoryResponse(id, name, icon))
        }

        return categoryList
    }

    companion object {
        private const val COLLECTION = "category"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_ICON = "icon"
    }
}
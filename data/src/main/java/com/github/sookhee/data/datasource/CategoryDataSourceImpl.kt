package com.github.sookhee.data.datasource

import android.util.Log
import com.github.sookhee.data.spec.CategoryResponse
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryDataSourceImpl: CategoryDataSource {
    override fun getCategoryList(): List<CategoryResponse> {
        val db = Firebase.firestore

        db.collection("category")
            .get()
            .addOnSuccessListener {
                it.forEach {
                    Log.i("민지", "${it.id} ==> ${it.data}")
                }
            }
            .addOnFailureListener {
                Log.i("민지", "ERROR, ${it.message}")
            }
        return listOf()
    }
}
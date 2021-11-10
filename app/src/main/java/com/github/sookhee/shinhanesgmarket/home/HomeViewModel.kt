package com.github.sookhee.shinhanesgmarket.home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.github.sookhee.domain.usecase.GetCategoryListUseCase

class HomeViewModel @ViewModelInject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase
) : ViewModel() {
    fun getCategoryList() {
        val result = getCategoryListUseCase()

        Log.i("민지", "result: $result")
    }
}
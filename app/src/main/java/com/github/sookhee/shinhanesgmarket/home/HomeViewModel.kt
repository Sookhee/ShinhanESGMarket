package com.github.sookhee.shinhanesgmarket.home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.domain.entity.Category
import com.github.sookhee.domain.usecase.GetCategoryListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase
) : ViewModel() {
    private val _categoryList = MutableLiveData<List<Category>>()
    val categoryList: LiveData<List<Category>>
        get() = _categoryList

    fun getCategoryList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getCategoryListUseCase()
                _categoryList.postValue(result)

            } catch (e: Exception) {
                Log.i(TAG, "getCategoryList Exception: $e")
            }
        }
    }

    companion object {
        private val TAG = HomeViewModel::class.simpleName
    }
}
package com.github.sookhee.shinhanesgmarket.home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.domain.entity.Category
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.domain.usecase.GetCategoryListUseCase
import com.github.sookhee.domain.usecase.GetProductListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val getProductListUseCase: GetProductListUseCase
) : ViewModel() {
    private val _categoryList = MutableLiveData<List<Category>>()
    val categoryList: LiveData<List<Category>>
        get() = _categoryList

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>>
        get() = _productList

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

    fun getProductList() {
        viewModelScope.launch {
            try {
                val result = getProductListUseCase()
                _productList.postValue(result)

                Log.i(TAG, "getProductList SUCCESS: $result")
            } catch (e: Exception) {
                Log.i(TAG, "getProductList Exception: $e")
            }
        }
    }

    companion object {
        private val TAG = HomeViewModel::class.simpleName
    }
}
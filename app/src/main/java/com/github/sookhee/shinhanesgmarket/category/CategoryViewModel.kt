package com.github.sookhee.shinhanesgmarket.category

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.domain.usecase.GetProductListWithQueryUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

class CategoryViewModel @ViewModelInject constructor(
    private val getProductListWithQueryUseCase: GetProductListWithQueryUseCase
): ViewModel() {
    private val _productList: MutableLiveData<List<Product>> = MutableLiveData()
    val productList: LiveData<List<Product>> = _productList

    private val _stateError = MutableLiveData<Boolean>()
    val stateError: LiveData<Boolean> = _stateError

    fun getProductListWithCategory(categoryId: String) {
        viewModelScope.launch {
            try {
                val result = getProductListWithQueryUseCase(key = "category_id", value = categoryId)
                _productList.value = result
            } catch (e: Exception) {
                Log.i("민지", "getProductListWithCategory Exception: $e")
                _stateError.value = true
            }
        }
    }
}
package com.github.sookhee.shinhanesgmarket.product

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.domain.usecase.GetProductDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel @ViewModelInject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
) : ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    fun getProductDetail(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getProductDetailUseCase(productId)
                _product.postValue(result)

            } catch (e: Exception) {
                Log.i(TAG, "getCategoryList Exception: $e")
            }
        }
    }

    companion object {
        private val TAG = ProductViewModel::class.simpleName
    }
}
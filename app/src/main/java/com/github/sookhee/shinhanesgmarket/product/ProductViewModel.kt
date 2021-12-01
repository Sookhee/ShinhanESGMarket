package com.github.sookhee.shinhanesgmarket.product

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.domain.usecase.GetProductDetailUseCase
import com.github.sookhee.domain.usecase.IsLikeProductUseCase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel @ViewModelInject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val isLikeProductUseCase: IsLikeProductUseCase,
) : ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    private val _isLikeProduct = MutableLiveData<String>()
    val isLikeProduct: LiveData<String>
        get() = _isLikeProduct

    fun getProductDetail(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getProductDetailUseCase(productId)
                _product.postValue(result)

            } catch (e: Exception) {
                Log.i(TAG, "getProductDetail Exception: $e")
            }
        }
    }

    fun isLikeProduct(userId: String, productId: String) {
        viewModelScope.launch {
            try {
                val result = isLikeProductUseCase(userId = userId, productId = productId)
                _isLikeProduct.value = result
            } catch (e: Exception) {
                Log.i(TAG, "isLikeProduct Exception: $e")
            }
        }
    }

    fun toggleProductHeart(userId: String) {
        val db = FirebaseFirestore.getInstance()

        if (_isLikeProduct.value?.isNotBlank() == true) {
            db.collection("user_like")
                .document(_isLikeProduct.value ?: "")
                .delete()
                .addOnSuccessListener { _isLikeProduct.value = "" }

        } else {
            val data = hashMapOf<String, String>()
            data["user_id"] = userId
            data["product_id"] = _product.value?.id ?: ""

            db.collection("user_like")
                .add(data)
                .addOnSuccessListener { _isLikeProduct.value = it.id }
        }
    }

    companion object {
        private val TAG = ProductViewModel::class.simpleName
    }
}
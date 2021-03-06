package com.github.sookhee.shinhanesgmarket.mypage

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.domain.usecase.GetLikeProductListUseCase
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.domain.usecase.GetProductListWithQueryUseCase
import kotlinx.coroutines.launch

class MyPageViewModel @ViewModelInject constructor(
    private val getProductListWithQueryUseCase: GetProductListWithQueryUseCase,
    private val getLikeProductListUseCase: GetLikeProductListUseCase
) : ViewModel() {
    private val _myProductList = MutableLiveData<List<Product>>()
    val myProductList: LiveData<List<Product>> = _myProductList

    private val _likeProductList = MutableLiveData<List<Product>>()
    val likeProductList: LiveData<List<Product>> = _likeProductList

    fun getMyProductList(employeeNo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getProductListWithQueryUseCase("feed_owner_id", employeeNo)
                _myProductList.postValue(result)
            } catch (e: Exception) {
                Log.i(TAG, "getMyProductList Exception: $e")
            }
        }
    }

    fun getLikeProductList(employeeNo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getLikeProductListUseCase(employeeNo)
                _likeProductList.postValue(result)

            } catch (e: Exception) {
                Log.i(TAG, "getLikeProductList Exception: $e")
            }
        }
    }

    companion object {
        private val TAG = MyPageViewModel::class.simpleName
    }
}
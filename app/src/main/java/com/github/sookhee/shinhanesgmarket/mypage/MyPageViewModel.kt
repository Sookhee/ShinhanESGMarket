package com.github.sookhee.shinhanesgmarket.mypage

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.sookhee.domain.usecase.GetMyProductListUseCase
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.domain.entity.Product
import kotlinx.coroutines.launch

class MyPageViewModel @ViewModelInject constructor(
    private val getMyProductListUseCase: GetMyProductListUseCase
): ViewModel() {
    private val _myProductList = MutableLiveData<List<Product>>()
    val myProductList: LiveData<List<Product>>
        get() = _myProductList

    fun getMyProductList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getMyProductListUseCase("feed_owner_id", "21200203")
                _myProductList.postValue(result)

            } catch (e: Exception) {
                Log.i(TAG, "getCategoryList Exception: $e")
            }
        }
    }

    companion object {
        private val TAG = MyPageViewModel::class.simpleName
    }
}
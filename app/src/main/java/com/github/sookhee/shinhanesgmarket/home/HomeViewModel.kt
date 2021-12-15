package com.github.sookhee.shinhanesgmarket.home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.domain.entity.Banner
import com.github.sookhee.domain.entity.Category
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.domain.usecase.GetBannerListUseCase
import com.github.sookhee.domain.usecase.GetCategoryListUseCase
import com.github.sookhee.domain.usecase.GetProductListUseCase
import com.github.sookhee.domain.usecase.GetProductListWithQueryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val getProductListUseCase: GetProductListUseCase,
    private val getBannerListUseCase: GetBannerListUseCase,
    private val getProductListWithQueryUseCase: GetProductListWithQueryUseCase
) : ViewModel() {
    private val _categoryList = MutableLiveData<List<Category>>()
    val categoryList: LiveData<List<Category>> = _categoryList

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> = _productList

    private val _bannerList = MutableLiveData<List<Banner>>()
    val bannerList: LiveData<List<Banner>> = _bannerList

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

    fun getProductList(distance: DISTANCE, communityCode: String = "") {
        viewModelScope.launch {
            try {
                val result = when (distance) {
                    DISTANCE.ALL -> {
                        getProductListUseCase()
                    }
                    DISTANCE.COMMUNITY -> {
                        getProductListWithQueryUseCase(key = "community_code", value = communityCode)
                    }
                    DISTANCE.FIVE_KM -> {
                        val tempList = getProductListUseCase()
                        tempList
                    }
                    DISTANCE.TEN_KM -> {
                        val tempList = getProductListUseCase()
                        tempList
                    }
                }
                _productList.postValue(result)

                Log.i(TAG, "getProductList SUCCESS: $result")
            } catch (e: Exception) {
                Log.i(TAG, "getProductList Exception: $e")
            }
        }
    }

    fun getBannerList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getBannerListUseCase()
                _bannerList.postValue(result)
            } catch (e: Exception) {
                Log.i(TAG, "getCategoryList Exception: $e")
            }
        }
    }

    companion object {
        private val TAG = HomeViewModel::class.simpleName
    }
}

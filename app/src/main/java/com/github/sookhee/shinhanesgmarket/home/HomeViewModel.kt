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
import com.github.sookhee.domain.entity.User
import com.github.sookhee.domain.usecase.GetBannerListUseCase
import com.github.sookhee.domain.usecase.GetCategoryListUseCase
import com.github.sookhee.domain.usecase.GetProductListUseCase
import com.github.sookhee.domain.usecase.GetProductListWithQueryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Math.*
import kotlin.math.pow

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

    fun getProductList(distance: DISTANCE, loginInfo: User) {
        viewModelScope.launch {
            try {
                val result = when (distance) {
                    DISTANCE.ALL -> {
                        getProductListUseCase()
                    }
                    DISTANCE.COMMUNITY -> {
                        getProductListWithQueryUseCase(key = "community_code", value = loginInfo.community_code)
                    }
                    DISTANCE.FIVE_KM -> {
                        getProductListUseCase().filter {
                            getDistance(it.area_latitude, it.area_longitude, loginInfo.latitude, loginInfo.longitude) <= 5000
                        }
                    }
                    DISTANCE.TEN_KM -> {
                        getProductListUseCase().filter {
                            getDistance(it.area_latitude, it.area_longitude, loginInfo.latitude, loginInfo.longitude) <= 10000
                        }
                    }
                }
                _productList.postValue(result)

                Log.i(TAG, "getProductList SUCCESS: $result")
            } catch (e: Exception) {
                Log.i(TAG, "getProductList Exception: $e")
            }
        }
    }

    private fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
        val R = 6372.8 * 1000

        val dLat = toRadians(lat2 - lat1)
        val dLon = toRadians(lon2 - lon1)
        val a = kotlin.math.sin(dLat / 2).pow(2.0) + kotlin.math.sin(dLon / 2).pow(2.0) * kotlin.math.cos(toRadians(lat1)) * kotlin.math.cos(toRadians(lat2))
        val c = 2 * kotlin.math.asin(kotlin.math.sqrt(a))

        return (R * c).toInt()
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

package com.github.sookhee.shinhanesgmarket.register

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.domain.entity.User
import com.github.sookhee.domain.usecase.RegisterProductUseCase
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class RegisterViewModel @ViewModelInject constructor(
    private val registerProductUseCase: RegisterProductUseCase
) : ViewModel() {
    private val _stateError = MutableLiveData<Boolean>()
    val stateError: LiveData<Boolean> = _stateError

    private val simpleDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())

    val title = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    fun registerProduct(loginInfo: User, photoList: HashMap<String, String>, category: String) {
        val product = Product(
            area_community_code = loginInfo.community_code,
            area_id = loginInfo.branch_no,
            area_latitude = loginInfo.latitude,
            area_longitude = loginInfo.longitude,
            area_name = loginInfo.branch_nm,
            category_id = category,
            content = content.value.toString(),
            created_at = simpleDate.format(Date(System.currentTimeMillis())),
            owner_id = loginInfo.employee_no,
            owner_name = loginInfo.name,
            photoList = photoList,
            price = price.value?.toInt() ?: 0,
            status = 0,
            title = title.value ?: "",
            updated_at = simpleDate.format(Date(System.currentTimeMillis()))
        )

        viewModelScope.launch {
            try {
                registerProductUseCase(product)
            } catch (e: Exception) {
                Log.i("민지", "registerProduct Exception: $e")
                _stateError.postValue(true)
            }
        }
    }
}
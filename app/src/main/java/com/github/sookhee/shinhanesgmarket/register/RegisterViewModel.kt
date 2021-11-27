package com.github.sookhee.shinhanesgmarket.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.domain.usecase.RegisterProductUseCase
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class RegisterViewModel @ViewModelInject constructor(
    private val registerProductUseCase: RegisterProductUseCase
) : ViewModel() {
    private val simpleDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())

    val title = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val category = MutableLiveData<Int>()

    fun registerProduct(photoList: HashMap<String, String>) {
        val product = Product(
            title = title.value ?: "",
            owner = "",
            price = price.value?.toInt() ?: 0,
            category = category.value ?: 0,
            status = 0,
            createdAt = simpleDate.format(Date(System.currentTimeMillis())),
            updatedAt = simpleDate.format(Date(System.currentTimeMillis())),
            area = "",
            content = content.value ?: "",
            photoList = photoList
        )

        viewModelScope.launch {
            try {
                registerProductUseCase(product)
            } catch (e: Exception) {
            }
        }
    }
}
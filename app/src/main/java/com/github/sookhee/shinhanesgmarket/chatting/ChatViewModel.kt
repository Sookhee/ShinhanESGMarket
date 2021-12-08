package com.github.sookhee.shinhanesgmarket.chatting

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.domain.entity.ChatPreview
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.domain.entity.User
import com.github.sookhee.domain.usecase.GetChatLogUseCase
import com.github.sookhee.domain.usecase.GetChatPreviewUseCase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class ChatViewModel @ViewModelInject constructor(
    private val getChatPreviewUseCase: GetChatPreviewUseCase,
    private val getChatLogUseCase: GetChatLogUseCase,
) : ViewModel() {

    private val _chatPreviewList = MutableLiveData<ChatPreview>()
    val chatPreviewList: LiveData<ChatPreview> = _chatPreviewList

    private val _isHaveRoom = MutableLiveData<Pair<Boolean, String>>()
    val isHaveRoom: LiveData<Pair<Boolean, String>> = _isHaveRoom

    fun getChatRoomPreviewList() {
        viewModelScope.launch {
            try {
                val result = getChatPreviewUseCase("21200203")
                result.map { it.seller_id == "21200203" || it.buyer_id == "21200203" }

                Log.i("민지", "result: $result")
            } catch (e: Exception) {
                Log.i("민지", "getChatRoomPreviewList EXCEPTION: $e")
            }
        }
    }

    fun createRoom(product: Product, buyer: User): String {
        val database = Firebase.database.getReference("room")
        val key = database.push().key

        key?.let {
            database.child(it).setValue(
                ChatPreview(
                    id = key,
                    product_id = product.id,
                    product_image = product.photoList["0"] ?: "",
                    seller_id = product.owner_id,
                    seller_name = product.owner,
                    seller_image = "",
                    seller_area = product.area,
                    buyer_id = buyer.employee_no,
                    buyer_name = buyer.nickname,
                    buyer_image = "",
                    last_message = "",
                    last_time = ""
                )
            )
        }

        _isHaveRoom.postValue(Pair(true, key ?: ""))

        return key ?: ""
    }

    fun checkIsHaveRoom(productId: String, userId: String) {
        viewModelScope.launch {
            try {
                Firebase.database.getReference("room").orderByChild("product_id").equalTo(productId)
                    .get()
                    .addOnSuccessListener {
                        if (it.value != null) {
                            (it.value as HashMap<String, HashMap<String, String>>).forEach { (key, room) ->
                                if (room["seller_id"] == userId || room["buyer_id"] == userId) {
                                    _isHaveRoom.postValue(Pair(true, room["id"] ?: ""))
                                }
                            }
                        }
                    }.addOnFailureListener {
                        Log.i("민지", "checkIsHaveRoom addOnFailureListener: $it")
                    }
            } catch (e: Exception) {
            }
        }
    }
}
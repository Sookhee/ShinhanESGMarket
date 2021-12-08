package com.github.sookhee.shinhanesgmarket.chatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.sookhee.domain.entity.ChatLog
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.shinhanesgmarket.AppApplication
import com.github.sookhee.shinhanesgmarket.databinding.ActivityChatRoomBinding
import com.github.sookhee.shinhanesgmarket.utils.setImageWithUrl
import com.github.sookhee.shinhanesgmarket.utils.withComma
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomBinding
    private lateinit var viewModel: ChatViewModel

    private var roomId: String = ""
    private val userLoginInfo = AppApplication.getInstance().getLoginInfo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        initChatPreviewActivity()
        setOnClickListener()
        // 채팅 목록 (product_id로 1차 거르고, user 두명)

        setContentView(binding.root)
    }

    private fun initChatPreviewActivity() {
        val intent = intent
        val title = intent.getStringExtra("product_title")
        val price = intent.getIntExtra("product_title", 0)
        val owner = intent.getStringExtra("product_owner")
        val photo = intent.getStringExtra("product_photo")

        binding.productPrice.text = "${price?.withComma()}원"
        binding.productName.text = title
        binding.traderName.text = owner
        binding.productImage.setImageWithUrl(photo ?: "")
    }

    private fun setOnClickListener() {
        binding.btnSend.setOnClickListener {
            if (roomId.isNotBlank()) {
                sendMessage(roomId)
            } else {
                val roomKey = viewModel.createRoom(
                    Product(
                        owner = intent.getStringExtra("product_owner") ?: "",
                        owner_id = intent.getStringExtra("product_owner_id") ?: "",
                        area = intent.getStringExtra("product_area") ?: "",
                        photoList = hashMapOf(Pair("0", intent.getStringExtra("product_photo") ?: ""))
                    ),
                    userLoginInfo
                )

                roomId = roomKey
                sendMessage(roomId)
            }
        }
    }

    private fun sendMessage(roomId: String) {
        Firebase.database.getReference("log")
            .push()
            .setValue(
                ChatLog(
                    id = "",
                    room_id = roomId,
                    sender_id = userLoginInfo.employee_no,
                    time = System.currentTimeMillis().toString(),
                    message = binding.chatEditText.text.toString(),
                    message_type = "MESSAGE"
                )
            )
    }
}
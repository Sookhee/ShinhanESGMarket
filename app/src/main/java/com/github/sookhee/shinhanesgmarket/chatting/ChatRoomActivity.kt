package com.github.sookhee.shinhanesgmarket.chatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.github.sookhee.domain.entity.ChatLog
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.shinhanesgmarket.AppApplication
import com.github.sookhee.shinhanesgmarket.databinding.ActivityChatRoomBinding
import com.github.sookhee.shinhanesgmarket.utils.setImageWithUrl
import com.github.sookhee.shinhanesgmarket.utils.withComma
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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
        setObserver()
        initChatRecyclerView()
        setOnClickListener()

        viewModel.checkIsHaveRoom(
            intent.getStringExtra("product_id") ?: "",
            userLoginInfo.employee_no
        )

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
            if (roomId.isNotBlank() || !viewModel.isHaveRoom.value?.second.isNullOrEmpty()) {
                if (roomId.isNullOrEmpty()) roomId = viewModel.isHaveRoom.value?.second ?: ""
                sendMessage(roomId)
            } else {
                val roomKey = viewModel.createRoom(
                    Product(
                        id = intent.getStringExtra("product_id") ?: "",
                        owner = intent.getStringExtra("product_owner") ?: "",
                        owner_id = intent.getStringExtra("product_owner_id") ?: "",
                        area = intent.getStringExtra("product_area") ?: "",
                        photoList = hashMapOf(
                            Pair(
                                "0",
                                intent.getStringExtra("product_photo") ?: ""
                            )
                        )
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
            ).addOnSuccessListener {
                binding.chatEditText.text = null
            }
    }

    private fun setObserver() {
        viewModel.isHaveRoom.observe(this) { isHaveRoom ->
            roomId = isHaveRoom.second
            setChatListener()
        }
    }

    private fun setChatListener() {
        val database = Firebase.database.getReference("log").orderByChild("room_id").equalTo(roomId)
        database.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val logHash = (snapshot.value as HashMap<String, String>)
                val log = ChatLog(
                    id = snapshot.key ?: "",
                    room_id = logHash["room_id"] ?: "",
                    sender_id = logHash["sender_id"] ?: "",
                    time = logHash["time"] ?: "",
                    message = logHash["message"] ?: "",
                    message_type = logHash["message_type"] ?: ""
                )

                (binding.logRecyclerView.adapter as ChatLogAdapter).addItem(log)
                binding.logRecyclerView.smoothScrollToPosition((binding.logRecyclerView.adapter as ChatLogAdapter).itemCount - 1)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.i("민지", "onChildChanged: ${snapshot.value}")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.i("민지", "onChildRemoved: ${snapshot.value}")

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.i("민지", "onChildMoved: ${snapshot.value}")

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("민지", "onCancelled: $error}")

            }
        })
    }

    private fun initChatRecyclerView() {
        binding.logRecyclerView.adapter = ChatLogAdapter()
    }
}
package com.github.sookhee.shinhanesgmarket.chatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.sookhee.shinhanesgmarket.databinding.ActivityChatRoomBinding

class ChatRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)

        initChatPreviewActivity()

        setContentView(binding.root)
    }

    private fun initChatPreviewActivity() {

    }
}
package com.github.sookhee.shinhanesgmarket.chatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.sookhee.shinhanesgmarket.databinding.ActivityChatRoomBinding
import com.github.sookhee.shinhanesgmarket.utils.setImageWithUrl
import com.github.sookhee.shinhanesgmarket.utils.withComma

class ChatRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)

        initChatPreviewActivity()

        setContentView(binding.root)
    }

    private fun initChatPreviewActivity() {
        val intent = intent
        val title = intent.getStringExtra("title")
        val price = intent.getIntExtra("price", 0)
        val owner = intent.getStringExtra("owner")
        val ownerId = intent.getStringExtra("owner_id")
        val photo = intent.getStringExtra("photo")

        binding.productPrice.text = "${price?.withComma()}원"
        binding.productName.text = title
        binding.traderName.text = owner
        binding.productImage.setImageWithUrl(photo ?: "")
    }
}
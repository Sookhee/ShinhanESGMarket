package com.github.sookhee.shinhanesgmarket.chatting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.sookhee.domain.entity.ChatPreview
import com.github.sookhee.shinhanesgmarket.databinding.FragmentChattingBinding

class ChattingFragment : Fragment() {
    private var _binding: FragmentChattingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChattingBinding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.chatPreviewRecyclerView.adapter = ChatPreviewAdapter().apply {
            items = CHAT_PREVIEW_LIST
            onItemClick = {
            }
        }

        binding.chatPreviewRecyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }

    companion object {
        private val CHAT_PREVIEW_LIST = listOf(
            ChatPreview("닉네임1", "화도읍", "10월 30일", "감사합니다~"),
            ChatPreview("닉네임2", "호평동", "10월 24일", "아직 안팔렸어요"),
            ChatPreview("닉네임3", "양서면", "10월 23일", "다행이네요. ~~~~~~~~~~~~~~~~~~~"),
        )
    }
}
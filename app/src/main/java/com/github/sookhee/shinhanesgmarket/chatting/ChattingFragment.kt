package com.github.sookhee.shinhanesgmarket.chatting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.sookhee.shinhanesgmarket.AppApplication
import com.github.sookhee.shinhanesgmarket.databinding.FragmentChattingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChattingFragment : Fragment() {
    private var _binding: FragmentChattingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ChatViewModel
    private val loginInfo = AppApplication.getInstance().getLoginInfo()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChattingBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        initRecyclerView()
        setObserver()

        viewModel.getChatRoomPreviewList(loginInfo.employee_no)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.chatPreviewRecyclerView.adapter = ChatPreviewAdapter().apply {
            onItemClick = {
                val intent = Intent(context, ChatRoomActivity::class.java)
                intent.putExtra("room_id", it.id)
                startActivity(intent)
            }
        }

        binding.chatPreviewRecyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }

    private fun setObserver() {
        viewModel.chatPreviewList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                (binding.chatPreviewRecyclerView.adapter as ChatPreviewAdapter).setItem(it)
            }
        }
    }
}
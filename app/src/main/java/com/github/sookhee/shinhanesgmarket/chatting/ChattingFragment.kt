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
        initSwipeLayout()
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
            employeeId = loginInfo.employee_no
            onItemClick = {
                val intent = Intent(context, ChatRoomActivity::class.java)
                intent.putExtra("room_id", it.id)
                intent.putExtra("product_id", it.product_id)
                intent.putExtra("product_image", it.product_image)
                intent.putExtra("product_title", it.product_title)
                intent.putExtra("product_price", it.product_price.toInt())
                intent.putExtra("product_owner", if (loginInfo.employee_no == it.seller_id) it.buyer_name else it.seller_name)
                startActivity(intent)
            }
        }

        binding.chatPreviewRecyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }

    private fun initSwipeLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getChatRoomPreviewList(loginInfo.employee_no)
        }
    }

    private fun setObserver() {
        viewModel.chatPreviewList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.swipeRefreshLayout.isRefreshing = false
                (binding.chatPreviewRecyclerView.adapter as ChatPreviewAdapter).setItem(it)
            }
        }
    }
}
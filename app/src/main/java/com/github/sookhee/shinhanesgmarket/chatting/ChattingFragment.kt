package com.github.sookhee.shinhanesgmarket.chatting

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.sookhee.shinhanesgmarket.AppApplication
import com.github.sookhee.shinhanesgmarket.R
import com.github.sookhee.shinhanesgmarket.databinding.FragmentChattingBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

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
            binding.swipeRefreshLayout.isRefreshing = false
            if (it.isNotEmpty()) {
                try {
                    (binding.chatPreviewRecyclerView.adapter as ChatPreviewAdapter).setItem(it)
                } catch (e: Exception) {
                    val dialog = Dialog(requireContext()).apply {
                        requestWindowFeature(Window.FEATURE_NO_TITLE)
                        setContentView(R.layout.layout_custom_dialog)
                    }

                    showErrorDialog(dialog)
                }
            }
        }
    }

    private fun showErrorDialog(dialog: Dialog) {
        dialog.show()

        dialog.findViewById<ImageView>(R.id.dialogImage).apply {
            setImageResource(R.drawable.dialog_exception)
            (layoutParams as LinearLayout.LayoutParams).setMargins(200, 0, 200, 0)
        }

        dialog.findViewById<TextView>(R.id.dialogText).text = getString(R.string.dialog_exception)

        dialog.findViewById<TextView>(R.id.dialogButton).apply {
            text = getString(R.string.dialog_close)
            setOnClickListener {
                dialog.dismiss()
            }
        }
    }
}
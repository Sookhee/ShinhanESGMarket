package com.github.sookhee.solcarrot.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.solcarrot.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setOnClickListener()
        initRecyclerView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListener() {
        binding.btnSearch.setOnClickListener { Toast.makeText(context, "SEARCH", Toast.LENGTH_SHORT).show() }
        binding.btnCategory.setOnClickListener { Toast.makeText(context, "CATEGORY", Toast.LENGTH_SHORT).show() }
        binding.btnAlarm.setOnClickListener { Toast.makeText(context, "ALARM", Toast.LENGTH_SHORT).show() }
    }

    private fun initRecyclerView() {
        binding.productRecyclerView.adapter = ProductAdapter().apply {
            items = PRODUCT_LIST
            onItemClick = {

            }
        }
    }

    companion object {
        private val PRODUCT_LIST = listOf(
            Product(0, "거실액자 팝니다", "오남읍", "12분 전", 30000),
            Product(1, "공주성 벙커침대", "호평동", "41분 전", 800000),
            Product(2, "갤럭시노트10 플러스 256", "진접읍", "1일 전", 350000),
            Product(3, "고양이 화장실", "화도읍", "31분 전", 34000),
            Product(4, "아이패드 미니 1세대", "오남읍", "56분 전", 30000),
            Product(5, "카카오프렌즈 인형(3개 일괄)", "진접읍", "55분 전", 18000),
        )
    }
}

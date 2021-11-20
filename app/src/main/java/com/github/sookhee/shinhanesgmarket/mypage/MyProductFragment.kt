package com.github.sookhee.shinhanesgmarket.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.sookhee.shinhanesgmarket.databinding.LayoutMypageBottomRecyclerviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProductFragment: Fragment() {
    private var _binding: LayoutMypageBottomRecyclerviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyPageViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutMypageBottomRecyclerviewBinding.inflate(inflater, container, false)

        initRecyclerView()
        setObserver()

        return binding.root
    }

    private fun setObserver() {
        viewModel.myProductList.observe(viewLifecycleOwner) {
            (binding.productRecyclerView.adapter as GridProductAdapter).setItem(it)
        }
    }

    private fun initRecyclerView() {
        binding.productRecyclerView.apply {
            adapter = GridProductAdapter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

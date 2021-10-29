package com.github.sookhee.solcarrot.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
}
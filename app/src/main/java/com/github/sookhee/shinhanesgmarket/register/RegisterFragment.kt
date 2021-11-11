package com.github.sookhee.shinhanesgmarket.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.shinhanesgmarket.R
import com.github.sookhee.shinhanesgmarket.databinding.FragmentRegisterBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        setOnClickListener()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SimpleDateFormat")
    private fun setOnClickListener() {
        binding.btnSubmit.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val category: Int? = 0
            val price = binding.priceEditText.text.toString()
            val content = binding.mainEditText.text.toString()
            val simpleDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val dateTime = simpleDate.format(Date(System.currentTimeMillis()))

            val needItem = mutableListOf<String>()
            if (title.isEmpty()) {
                needItem.add("제목")
            }
            if (category == null) {
                needItem.add("카테고리")
            }
            if (price.isEmpty()) {
                needItem.add("가격")
            }
            if (content.isEmpty()) {
                needItem.add("내용")
            }

            if (needItem.size == 0) {
                val product = Product(
                    id = 0,
                    title = title,
                    price = price.toInt(),
                    owner = "정민지",
                    category = 3,
                    status = 0,
                    createdAt = dateTime
                )

                val db = FirebaseFirestore.getInstance()
                    .collection("product")
                    .add(product)
                    .addOnSuccessListener {
                        Toast.makeText(
                            context,
                            resources.getString(R.string.string_submit_success),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnCanceledListener {
                        Toast.makeText(
                            context,
                            "...실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                Toast.makeText(
                    context,
                    resources.getString(R.string.hint_need_input, TextUtils.join(", ", needItem)),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

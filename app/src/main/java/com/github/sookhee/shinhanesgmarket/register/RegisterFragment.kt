package com.github.sookhee.shinhanesgmarket.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.github.sookhee.shinhanesgmarket.AppApplication
import com.github.sookhee.shinhanesgmarket.MainActivity
import com.github.sookhee.shinhanesgmarket.R
import com.github.sookhee.shinhanesgmarket.adapter.PhotoAdapter
import com.github.sookhee.shinhanesgmarket.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RegisterViewModel

    private val app = AppApplication.getInstance()
    private val loginInfo = app.getLoginInfo()

    private val photoList = hashMapOf<String, String>()
    private var category: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.vm = viewModel

        setOnClickListener()
        initPhotoRecyclerView()
        initSpinner()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == CODE_SELECT_IMAGE) {
            photoList.clear()
            for (i in 0 until data?.clipData?.itemCount!!) {
                photoList["${loginInfo.employee_no}_${System.currentTimeMillis()}_$i.png"] =
                    data.clipData!!.getItemAt(i).uri.toString()
            }

            (binding.photoRecyclerView.adapter as PhotoAdapter).setItem(photoList.values.toList())
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setOnClickListener() {
        binding.btnSubmit.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val category = category
            val price = binding.priceEditText.text.toString()
            val content = binding.mainEditText.text.toString()

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
                viewModel.registerProduct(loginInfo, photoList, category ?: 0)

                Toast.makeText(
                    context,
                    "등록 성공",
                    Toast.LENGTH_SHORT
                ).show()

                binding.titleEditText.text = null
                binding.mainEditText.text = null
                binding.priceEditText.text = null

                (activity as MainActivity).setFragment((activity as MainActivity).homeFragment)
            } else {
                Toast.makeText(
                    context,
                    resources.getString(R.string.hint_need_input, TextUtils.join(", ", needItem)),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnAddPhoto.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK).apply {
                type = MediaStore.Images.Media.CONTENT_TYPE
                data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }

            startActivityForResult(galleryIntent, CODE_SELECT_IMAGE)
        }
    }

    private fun initPhotoRecyclerView() {
        val photoAdapter = PhotoAdapter()

        binding.photoRecyclerView.apply {
            adapter = photoAdapter
        }
    }

    private fun initSpinner() {
        val categoryList = arrayOf("기부", "무료나눔", "도서", "생활가전", "디지털기기", "스포츠", "여성의류", "남성의류")
        binding.categorySpinner.apply {
            adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, categoryList)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    category = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }

    companion object {
        private const val CODE_SELECT_IMAGE = 10001
    }
}

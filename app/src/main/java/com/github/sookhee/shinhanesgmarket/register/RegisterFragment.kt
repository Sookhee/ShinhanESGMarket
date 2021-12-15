package com.github.sookhee.shinhanesgmarket.register

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
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
    private var category: String? = null

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

        setObserver()

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
            binding.photoCountText.text = "${photoList.values.toList().size}/10"
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
                viewModel.registerProduct(loginInfo, photoList, category ?: "")
                Toast.makeText(
                    context,
                    "등록중",
                    Toast.LENGTH_SHORT
                ).show()
                binding.btnSubmit.isClickable = false
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
        val categoryList = AppApplication.getInstance().getCategoryList()
        binding.categorySpinner.apply {
            adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, categoryList.map {it.name})
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    category = categoryList[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }

    private fun setObserver() {
        viewModel.stateError.observe(viewLifecycleOwner) {
            if (it) {
                val dialog = Dialog(requireContext()).apply {
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    setContentView(R.layout.layout_custom_dialog)
                }

                showErrorDialog(dialog)
            }
        }

        viewModel.stateSuccess.observe(viewLifecycleOwner) {
            binding.btnSubmit.isClickable = true
            if (it){
                Toast.makeText(
                    context,
                    "등록 성공",
                    Toast.LENGTH_SHORT
                ).show()

                binding.titleEditText.text = null
                binding.mainEditText.text = null
                binding.priceEditText.text = null
                binding.photoCountText.text = "0/10"
                (binding.photoRecyclerView.adapter as PhotoAdapter).setItem(emptyList())

                (activity as MainActivity).setHomeFragmentFocus()
            } else {
                Toast.makeText(
                    context,
                    "등록 실패ㅠㅠ 다시 시도해주세요",
                    Toast.LENGTH_SHORT
                ).show()
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

    companion object {
        private const val CODE_SELECT_IMAGE = 10001
    }
}

package com.github.sookhee.shinhanesgmarket.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.sookhee.shinhanesgmarket.R
import com.github.sookhee.shinhanesgmarket.adapter.PhotoAdapter
import com.github.sookhee.shinhanesgmarket.databinding.FragmentRegisterBinding
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RegisterViewModel

    private val photoList = hashMapOf<String, Uri?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.vm = viewModel

        setOnClickListener()
        initPhotoRecyclerView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == CODE_SELECT_IMAGE) {
            for (i in 0 until data?.clipData?.itemCount!!) {
                photoList["21200203_${System.currentTimeMillis()}_$i.png"] =
                    data.clipData!!.getItemAt(i).uri
            }

            (binding.photoRecyclerView.adapter as PhotoAdapter).setItem(photoList.values.toList())
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setOnClickListener() {
        binding.btnSubmit.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val category: Int? = 0
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
                viewModel.registerProduct()

                Toast.makeText(
                    context,
                    "등록 성공",
                    Toast.LENGTH_SHORT
                ).show()
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

    private fun uploadImage() {
        val storage = FirebaseStorage.getInstance()

        photoList.forEach { photo ->
            val riverRef = storage.reference.child("/product_image/${photo.key}")

            photo.value?.let {
                riverRef.putFile(it)
                    .addOnFailureListener { Log.i("민지", "FAIL: $it") }
                    .addOnSuccessListener {
                        Log.i("민지", "SUCCESS : ${photo.key}")

                        storage.getReference(photo.key).downloadUrl.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Glide.with(this)
                                    .load(task.result)
                                    .into(binding.photoIcon)
                            }
                        }
                    }
            }
        }
    }

    private fun initPhotoRecyclerView() {
        val photoAdapter = PhotoAdapter()

        binding.photoRecyclerView.apply {
            adapter = photoAdapter
        }
    }

    companion object {
        private const val CODE_SELECT_IMAGE = 10001
    }
}

package com.github.sookhee.shinhanesgmarket.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.sookhee.domain.entity.Category
import com.github.sookhee.shinhanesgmarket.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)

        setOnClickListener()
        initCategoryRecyclerView()

        setContentView(binding.root)
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener { onBackPressed() }
    }

    private fun initCategoryRecyclerView() {
        binding.categoryRecyclerView.adapter = CategoryAdapter().apply {
            items = CATEGORY_LIST
            onItemClick = {
            }
        }
    }

    companion object {
        private val CATEGORY_LIST = listOf(
            Category("디지털기기"),
            Category("인기매물"),
            Category("생활가전"),
            Category("가구/인테리어"),
            Category("유아동"),
            Category("생활/가공식품"),
            Category("유아도서"),
            Category("스포츠/레저"),
            Category("여성잡화"),
            Category("여성의류"),
            Category("남성패션/잡화"),
            Category("게임/취미"),
            Category("뷰티/미용"),
            Category("반려동물용품"),
            Category("도서/티켓/음반"),
            Category("식물"),
            Category("기타 중고물품"),
            Category("삽니다"),
        )
    }
}
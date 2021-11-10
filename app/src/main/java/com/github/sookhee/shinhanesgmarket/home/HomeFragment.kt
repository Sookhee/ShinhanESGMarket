package com.github.sookhee.shinhanesgmarket.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.github.sookhee.domain.entity.Category
import com.github.sookhee.domain.entity.Product
import com.github.sookhee.shinhanesgmarket.R
import com.github.sookhee.shinhanesgmarket.alarm.AlarmActivity
import com.github.sookhee.shinhanesgmarket.databinding.FragmentHomeBinding
import com.github.sookhee.shinhanesgmarket.product.ProductActivity
import com.github.sookhee.shinhanesgmarket.search.SearchActivity
import com.github.sookhee.shinhanesgmarket.utils.heightAnimation
import com.github.sookhee.shinhanesgmarket.utils.setGone
import com.github.sookhee.shinhanesgmarket.utils.setVisible

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var gridLineCount = (CATEGORY_LIST.size + GRID_SPAN_COUNT - 1) / GRID_SPAN_COUNT

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setOnClickListener()

        initBanner()
        initBannerIndicator()

        initProductRecyclerView()

        initCategoryRecyclerView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListener() {
        binding.btnSearch.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.btnAlarm.setOnClickListener {
            val intent = Intent(context, AlarmActivity::class.java)
            startActivity(intent)
        }
        binding.categoryCollapsed.setOnClickListener {
            expandView()
        }

        binding.categoryExpanded.setOnClickListener {
            collapseView()
        }
    }

    private fun initBanner() {
        binding.banner.apply {
            adapter = ViewPagerAdapter().apply {
                items = BANNER_LIST
                onItemClick = {
                    Toast.makeText(context, "banner: $it", Toast.LENGTH_SHORT).show()
                }
            }

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                    binding.bannerIndicator.x =
                        (position + positionOffset) * binding.bannerIndicator.width
                }
            })
        }
    }

    private fun initBannerIndicator() {
        binding.bannerIndicator.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val layoutParams = binding.bannerIndicator.layoutParams
                layoutParams.width = binding.bannerIndicatorBackground.width / BANNER_LIST.size
                binding.bannerIndicator.layoutParams = layoutParams

                binding.banner.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun initProductRecyclerView() {
        binding.productRecyclerView.adapter = ProductAdapter().apply {
            items = PRODUCT_LIST
            onItemClick = {
                val intent = Intent(context, ProductActivity::class.java)

                startActivity(intent)
            }
        }
    }

    private fun initCategoryRecyclerView() {
        binding.categoryRecyclerView.apply {
            adapter = CategoryAdapter().apply {
                items = CATEGORY_LIST
                onItemClick = {
                    Toast.makeText(context, "category: ${it.name}", Toast.LENGTH_SHORT).show()
                }
            }

            layoutManager = object : GridLayoutManager(requireContext(), 4) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
        }
    }

    private fun expandView() {
        binding.categoryExpanded.setVisible()
        binding.categoryCollapsed.setGone()
        binding.categoryRecyclerView.heightAnimation(
            resources.getDimensionPixelSize(R.dimen.category_item_height) * gridLineCount
        )
    }

    private fun collapseView() {
        binding.categoryExpanded.setGone()
        binding.categoryCollapsed.setVisible()
        binding.categoryRecyclerView.heightAnimation(
            resources.getDimensionPixelSize(R.dimen.category_item_height)
        )
    }


    companion object {
        private val BANNER_LIST = mutableListOf("TEST1", "TEST2", "TEST3")
        private val PRODUCT_LIST = listOf(
            Product(
                id = 1,
                title = "맥북에어 m1 미개봉 상품 팔아여",
                owner = "머드더스튜던트",
                price = 950000,
                category = 0,
                status = 0,
                createdAt = "20211110",
                updatedAt = "",
                area = "왕십리"
            ),
            Product(
                id = 2,
                title = "도리벤 바지 아크릴 삽니당",
                owner = "이수린",
                price = 10000,
                category = 0,
                status = 0,
                createdAt = "20211110",
                updatedAt = "",
                area = "신도림"
            ),
            Product(
                id = 3,
                title = "쿠로미 인형",
                owner = "신스",
                price = 4000,
                category = 0,
                status = 0,
                createdAt = "20211110",
                updatedAt = "",
                area = "강남동"
            ),
            Product(
                id = 4,
                title = "졸려여",
                owner = "정민지",
                price = 0,
                category = 0,
                status = 0,
                createdAt = "20211110",
                updatedAt = "",
                area = "화도읍"
            ),
        )
        private const val GRID_SPAN_COUNT = 4
        private val CATEGORY_LIST = listOf(
            Category(0, "디지털기기", ""),
            Category(1, "인기매물", ""),
            Category(2, "생활가전", ""),
            Category(3, "가구/인테리어", ""),
            Category(4, "유아동", ""),
            Category(5, "생활/가공식품", ""),
            Category(6, "유아도서", ""),
            Category(7, "스포츠/레저", ""),
            Category(8, "여성잡화", ""),
            Category(9, "여성의류", ""),
            Category(10, "남성패션/잡화", ""),
            Category(11, "게임/취미", ""),
            Category(12, "뷰티/미용", ""),
            Category(13, "반려동물용품", ""),
            Category(14, "도서/티켓/음반", ""),
            Category(15, "식물", ""),
            Category(16, "기타 중고물품", ""),
            Category(17, "삽니다", ""),
        )
    }
}

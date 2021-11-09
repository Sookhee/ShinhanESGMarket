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
            Product(0, "거실액자 팝니다", "오남읍", "12분 전", 30000),
            Product(1, "공주성 벙커침대", "호평동", "41분 전", 800000),
            Product(2, "갤럭시노트10 플러스 256", "진접읍", "1일 전", 350000),
            Product(3, "고양이 화장실", "화도읍", "31분 전", 34000),
            Product(4, "아이패드 미니 1세대", "오남읍", "56분 전", 30000),
            Product(5, "카카오프렌즈 인형(3개 일괄)", "진접읍", "55분 전", 18000),
        )
        private const val GRID_SPAN_COUNT = 4
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

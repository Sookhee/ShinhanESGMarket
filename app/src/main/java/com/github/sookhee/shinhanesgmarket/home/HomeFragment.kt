package com.github.sookhee.shinhanesgmarket.home

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.github.sookhee.domain.entity.Banner
import com.github.sookhee.shinhanesgmarket.R
import com.github.sookhee.shinhanesgmarket.alarm.AlarmActivity
import com.github.sookhee.shinhanesgmarket.databinding.FragmentHomeBinding
import com.github.sookhee.shinhanesgmarket.product.ProductActivity
import com.github.sookhee.shinhanesgmarket.search.SearchActivity
import com.github.sookhee.shinhanesgmarket.utils.heightAnimation
import com.github.sookhee.shinhanesgmarket.utils.setGone
import com.github.sookhee.shinhanesgmarket.utils.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    private var gridLineCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.getCategoryList()
        viewModel.getProductList()

        setOnClickListener()
        observeFlow()

        initBanner()
        initBannerIndicator(BANNER_LIST.size)

        initProductRecyclerView()

        initCategoryRecyclerView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeFlow() {
        viewModel.categoryList.observe(viewLifecycleOwner) {
            (binding.categoryRecyclerView.adapter as CategoryAdapter).setItem(it)
            gridLineCount = (it.size + GRID_SPAN_COUNT - 1) / GRID_SPAN_COUNT
        }

        viewModel.productList.observe(viewLifecycleOwner) {
            (binding.productRecyclerView.adapter as ProductAdapter).setItem(it)
        }
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

        binding.spinner.setOnClickListener {
            if (binding.spinnerExpanded.isVisible) {
                binding.spinnerExpanded.setGone()
            } else {
                binding.spinnerExpanded.setVisible()
            }
        }
    }

    private fun initBanner() {
        binding.banner.apply {
            adapter = ViewPagerAdapter()
                .apply {
                    items = BANNER_LIST
                    onItemClick = {
                        Toast.makeText(context, "click: $it", Toast.LENGTH_SHORT).show()
                    }
                }
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            clipToPadding = false
            offscreenPageLimit = 1
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val cardOffset = resources.getDimension(R.dimen.sol_main_card_offset)
            val nextItemVisiblePx = resources.getDimension(R.dimen.sol_main_card_margin)
            val pageTranslationX = (cardOffset * VIEWPAGER_PRE_VIEW) + nextItemVisiblePx

            setCurrentItem((adapter as ViewPagerAdapter).firstElementPosition, false)

            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State,
                ) {
                    outRect.right = cardOffset.toInt() + nextItemVisiblePx.toInt()
                    outRect.left = cardOffset.toInt() + nextItemVisiblePx.toInt()
                }
            })

            setPageTransformer { page, position ->
                page.translationX = -pageTranslationX * (position)
            }

            val runnable = Runnable {
                currentItem += 1
            }

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position % ((this@apply).adapter as ViewPagerAdapter).itemCount)

                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable, 3000)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        ViewPager2.SCROLL_STATE_IDLE -> {
                            handler.removeCallbacks(runnable)
                            handler.postDelayed(runnable, 3000)
                        }

                        ViewPager2.SCROLL_STATE_DRAGGING -> {
                            handler.removeCallbacks(runnable)
                        }

                        ViewPager2.SCROLL_STATE_SETTLING -> {
                            // do nothing
                        }
                    }
                }
            })
        }
    }

    private fun initBannerIndicator(bannerSize: Int) {
        binding.bannerIndicatorBackground.post {
            val width = binding.bannerIndicatorBackground.measuredWidth
            val barWidth = if (bannerSize != 0) {
                width / bannerSize
            } else {
                width
            }

            val indicatorBarLayoutParams = binding.bannerIndicator.layoutParams
            indicatorBarLayoutParams.width = barWidth

            val subIndicatorBarLayoutParams = binding.bannerSubIndicator.layoutParams
            subIndicatorBarLayoutParams.width = barWidth

            binding.bannerIndicator.layoutParams = indicatorBarLayoutParams
            binding.bannerSubIndicator.layoutParams = subIndicatorBarLayoutParams
        }

        binding.banner.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                binding.bannerIndicator.translationX =
                    ((position % bannerSize) + positionOffset) * binding.bannerIndicator.width

                binding.bannerSubIndicator.translationX =
                    ((position % bannerSize) + positionOffset) * binding.bannerIndicator.width - binding.bannerIndicatorBackground.width
            }
        })
    }

    private fun initProductRecyclerView() {
        binding.productRecyclerView.adapter = ProductAdapter().apply {
            onItemClick = {
                val intent = Intent(context, ProductActivity::class.java)
                intent.putExtra("PRODUCT_ID", it.id)
                startActivity(intent)
            }
        }
    }

    private fun initCategoryRecyclerView() {
        binding.categoryRecyclerView.apply {
            adapter = CategoryAdapter().apply {
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
        private val BANNER_LIST = mutableListOf(
            Banner(
                title = "겉옷 기부 캠페인",
                message = "추운 겨울<br/>따뜻한 마음모아<br/>함께 이겨내요",
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/doremi-market.appspot.com/o/banner%2Fbanner_character_1.png?alt=media&token=b4c1e3e2-35f8-4b17-a1d2-648e5c3aa844",
                hookMessage = "자세한 내용은 골드윙 게시판 참고",
                emoji = "&#x26C4",
                backgroundColor = "#4ea1ee"
            ),
            Banner(
                title = "학용품 기부 함께해요",
                message = "안쓰는 학용품<br/>하나 둘 모아<br/>큰 힘 만들어요",
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/doremi-market.appspot.com/o/banner%2Fbanner_character_2.png?alt=media&token=829f89e5-0974-46c5-b247-e65655dcf8a7",
                hookMessage = "자세한 내용은 골드윙 게시판 참고",
                emoji = "&#x270D",
                backgroundColor = "#8084f1"
            ),
            Banner(
                title = "타이틀3",
                message = "메시지<br/>두번째 줄<br/>세번째 줄",
                imageUrl = "",
                hookMessage = "자세한 내용은 골드윙 게시판 참고",
                emoji = "&#x2728",
                backgroundColor = "#2fb3dd"
            )
        )
        private const val GRID_SPAN_COUNT = 4
        private const val VIEWPAGER_PRE_VIEW = 3
    }
}

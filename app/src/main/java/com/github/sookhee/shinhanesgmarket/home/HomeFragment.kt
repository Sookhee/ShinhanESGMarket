package com.github.sookhee.shinhanesgmarket.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
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
        initBannerIndicator()

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
        private val BANNER_LIST = mutableListOf("TEST1", "TEST2", "TEST3")
        private const val GRID_SPAN_COUNT = 4
    }
}

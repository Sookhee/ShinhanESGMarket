package com.github.sookhee.shinhanesgmarket.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.github.sookhee.domain.entity.Banner
import com.github.sookhee.shinhanesgmarket.alarm.AlarmActivity
import com.github.sookhee.shinhanesgmarket.databinding.FragmentHomeBinding
import com.github.sookhee.shinhanesgmarket.product.ProductActivity
import com.github.sookhee.shinhanesgmarket.search.SearchActivity
import com.github.sookhee.shinhanesgmarket.utils.setGone
import com.github.sookhee.shinhanesgmarket.utils.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.getBannerList()
        viewModel.getCategoryList()
        viewModel.getProductList()

        setOnClickListener()
        observeFlow()

        initProductRecyclerView()
        initCategoryRecyclerView()
        initRefreshLayout()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeFlow() {
        viewModel.categoryList.observe(viewLifecycleOwner) {
            (binding.categoryRecyclerView.adapter as CategoryAdapter).setItem(it)
        }

        viewModel.productList.observe(viewLifecycleOwner) {
            (binding.productRecyclerView.adapter as ProductAdapter).setItem(it)

            binding.refreshLayout.isRefreshing = false
        }

        viewModel.bannerList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                initBanner(it)
                initBannerIndicator(it.size)
            }
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

        binding.spinner.setOnClickListener {
            if (binding.spinnerExpanded.isVisible) {
                binding.spinnerExpanded.setGone()
            } else {
                binding.spinnerExpanded.setVisible()
            }
        }
    }

    private fun initBanner(list: List<Banner>) {
        binding.banner.apply {
            adapter = ViewPagerAdapter()
                .apply {
                    setItem(list)
                    onItemClick = {
                        Toast.makeText(context, "click: $it", Toast.LENGTH_SHORT).show()
                    }
                }
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            clipToPadding = false
            offscreenPageLimit = 1
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            setCurrentItem((adapter as ViewPagerAdapter).firstElementPosition, false)

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
        }
    }

    private fun initRefreshLayout() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getProductList()
        }
    }
}

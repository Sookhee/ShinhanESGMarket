package com.github.sookhee.shinhanesgmarket.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.view.marginStart
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.github.sookhee.domain.entity.Banner
import com.github.sookhee.domain.entity.User
import com.github.sookhee.shinhanesgmarket.AppApplication
import com.github.sookhee.shinhanesgmarket.R
import com.github.sookhee.shinhanesgmarket.alarm.AlarmActivity
import com.github.sookhee.shinhanesgmarket.category.CategoryActivity
import com.github.sookhee.shinhanesgmarket.databinding.FragmentHomeBinding
import com.github.sookhee.shinhanesgmarket.product.ProductActivity
import com.github.sookhee.shinhanesgmarket.search.SearchActivity
import com.github.sookhee.shinhanesgmarket.utils.setGone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    private var loginInfo: User = User()
    private var distance: DISTANCE = DISTANCE.ALL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.getBannerList()
        viewModel.getCategoryList()

        observeFlow()

        initProductRecyclerView()
        initCategoryRecyclerView()
        initRefreshLayout()
        initDistanceSpinner()

        loginInfo = AppApplication.getInstance().getLoginInfo()

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

        viewModel.stateError.observe(viewLifecycleOwner) {
            if (it) {
                val dialog = Dialog(requireContext()).apply {
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    setContentView(R.layout.layout_custom_dialog)
                }

                showErrorDialog(dialog)
            }
        }
    }

    private fun initBanner(list: List<Banner>) {
        binding.banner.apply {
            adapter = ViewPagerAdapter()
                .apply {
                    setItem(list)
                    onItemClick = {
                        val dialog = Dialog(context).apply {
                            requestWindowFeature(Window.FEATURE_NO_TITLE)
                            setContentView(R.layout.layout_custom_dialog)
                        }

                        showBannerDialog(dialog)
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

    private fun showBannerDialog(dialog: Dialog) {
        dialog.show()

        dialog.findViewById<ImageView>(R.id.dialogImage).apply {
            setImageResource(R.drawable.dialog_coming_soon)
            layoutParams.apply {
                height = 800
            }
        }

        dialog.findViewById<TextView>(R.id.dialogText).setGone()

        dialog.findViewById<TextView>(R.id.dialogButton).apply {
            text = getString(R.string.dialog_coming_soon)
            setOnClickListener {
                dialog.dismiss()
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
                    val intent = Intent(context, CategoryActivity::class.java)
                    intent.putExtra("category_id", it.id)
                    intent.putExtra("category_name", it.name)

                    startActivity(intent)
                }
            }
        }
    }

    private fun initRefreshLayout() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getProductList(distance, loginInfo)
        }
    }

    private fun initDistanceSpinner() {
        val distanceList = arrayListOf<Pair<String, DISTANCE>>()
        if (loginInfo.community_code == "0" || loginInfo.community_code.isBlank()) { // 커뮤니티 없으면
            distanceList.add(Pair("5km", DISTANCE.FIVE_KM))
            distanceList.add(Pair("10km", DISTANCE.TEN_KM))
            distanceList.add(Pair("전체", DISTANCE.ALL))
        } else if (loginInfo.latitude == 0.0 || loginInfo.longitude == 0.0) { // 주소 없으면
            distanceList.add(Pair("전체", DISTANCE.ALL))
        } else { // 일반적
            distanceList.add(Pair("5km", DISTANCE.FIVE_KM))
            distanceList.add(Pair("10km", DISTANCE.TEN_KM))
            distanceList.add(Pair("커뮤니티", DISTANCE.COMMUNITY))
            distanceList.add(Pair("전체", DISTANCE.ALL))
        }

        binding.distanceSpinner.apply {
            adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, distanceList.map { it.first })
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    distance = distanceList[position].second
                    viewModel.getProductList(distance, loginInfo)
                    binding.refreshLayout.isRefreshing = true
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }
}

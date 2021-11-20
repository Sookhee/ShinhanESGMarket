package com.github.sookhee.shinhanesgmarket.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.github.sookhee.shinhanesgmarket.R
import com.github.sookhee.shinhanesgmarket.databinding.FragmentMypageBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageFragment : Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyPageViewModel by viewModels()

    private val myProductFragment by lazy { MyProductFragment() }
    private val likeProductFragment by lazy { LikeProductFragment() }
    private var activeFragment: Fragment = myProductFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)

        setOnClickListener()
        initTabLayout()

        viewModel.getMyProductList()
        viewModel.getLikeProductList()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListener() {
        binding.btnSetting.setOnClickListener {
            Toast.makeText(
                context,
                "SETTING",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initTabLayout() {
        binding.tabLayout.apply {
            addTab(this.newTab().setText("판매내역"))
            addTab(this.newTab().setText("관심목록"))

            addOnTabSelectedListener(
                object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        when (tab.position) {
                            0 -> changeFragment(myProductFragment)
                            else -> changeFragment(likeProductFragment)
                        }
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }
                })

            changeFragment(activeFragment)
        }
    }

    private fun changeFragment(currentFragment: Fragment) {
        val fm = childFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).hide(activeFragment)

        if (!currentFragment.isAdded) {
            fm.add(R.id.frameLayout, currentFragment, currentFragment.toString())
                .show(currentFragment).commit()
        } else if (activeFragment != currentFragment) {
            fm.show(currentFragment).commit()
        }

        activeFragment = currentFragment
    }
}

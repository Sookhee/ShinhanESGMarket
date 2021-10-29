package com.github.sookhee.solcarrot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.sookhee.solcarrot.chatting.ChattingFragment
import com.github.sookhee.solcarrot.databinding.ActivityMainBinding
import com.github.sookhee.solcarrot.home.HomeFragment
import com.github.sookhee.solcarrot.mypage.MypageFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val homeFragment by lazy { HomeFragment() }
    private val mypageFragment by lazy { MypageFragment() }
    private val chattingFragment by lazy { ChattingFragment() }
    private var activeFragment = homeFragment as Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setOnClickListener()
        setFragment(activeFragment)

        setContentView(binding.root)
    }

    private fun setOnClickListener() {
        binding.home.setOnClickListener {
            setFragment(homeFragment)
        }

        binding.chatting.setOnClickListener {
            setFragment(chattingFragment)
        }

        binding.mypage.setOnClickListener {
            setFragment(mypageFragment)
        }
    }

    private fun setFragment(currentFragment: Fragment) {
        val fm = supportFragmentManager.beginTransaction()
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
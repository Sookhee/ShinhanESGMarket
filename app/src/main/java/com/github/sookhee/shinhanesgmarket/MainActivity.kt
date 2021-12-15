package com.github.sookhee.shinhanesgmarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.sookhee.shinhanesgmarket.chatting.ChattingFragment
import com.github.sookhee.shinhanesgmarket.databinding.ActivityMainBinding
import com.github.sookhee.shinhanesgmarket.home.HomeFragment
import com.github.sookhee.shinhanesgmarket.mypage.MypageFragment
import com.github.sookhee.shinhanesgmarket.register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val homeFragment by lazy { HomeFragment() }
    private val registerFragment by lazy { RegisterFragment() }
    private val mypageFragment by lazy { MypageFragment() }
    private val chattingFragment by lazy { ChattingFragment() }
    private var activeFragment = homeFragment as Fragment

    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setOnClickListener()
        setFragment(activeFragment)

        setContentView(binding.root)
    }

    private fun setOnClickListener() {
        binding.home.setOnClickListener {
            setHomeFragmentFocus()
        }

        binding.register.setOnClickListener {
            setFragment(registerFragment)
            binding.homeImage.setImageResource(R.drawable.ic_home_off)
            binding.registerImage.setImageResource(R.drawable.ic_plus_on)
            binding.chattingImage.setImageResource(R.drawable.ic_chat_off)
            binding.mypageImage.setImageResource(R.drawable.ic_user_off)
        }

        binding.chatting.setOnClickListener {
            setFragment(chattingFragment)
            binding.homeImage.setImageResource(R.drawable.ic_home_off)
            binding.registerImage.setImageResource(R.drawable.ic_plus_off)
            binding.chattingImage.setImageResource(R.drawable.ic_chat_on)
            binding.mypageImage.setImageResource(R.drawable.ic_user_off)
        }

        binding.mypage.setOnClickListener {
            setFragment(mypageFragment)
            binding.homeImage.setImageResource(R.drawable.ic_home_off)
            binding.registerImage.setImageResource(R.drawable.ic_plus_off)
            binding.chattingImage.setImageResource(R.drawable.ic_chat_off)
            binding.mypageImage.setImageResource(R.drawable.ic_user_on)
        }
    }

    fun setHomeFragmentFocus() {
        setFragment(homeFragment)
        binding.homeImage.setImageResource(R.drawable.ic_home_on)
        binding.registerImage.setImageResource(R.drawable.ic_plus_off)
        binding.chattingImage.setImageResource(R.drawable.ic_chat_off)
        binding.mypageImage.setImageResource(R.drawable.ic_user_off)
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
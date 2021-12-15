package com.github.sookhee.shinhanesgmarket.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ViewPropertyAnimator
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.github.sookhee.shinhanesgmarket.AppApplication
import com.github.sookhee.shinhanesgmarket.MainActivity
import com.github.sookhee.shinhanesgmarket.R
import com.github.sookhee.shinhanesgmarket.user.UserLoginActivity
import com.github.sookhee.shinhanesgmarket.user.UserViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel

    private lateinit var loginIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        startSplashAnimation()
        checkLoginState()
        setObserver()
    }

    private fun startSplashAnimation() {
        val splashImage = findViewById<ImageView>(R.id.splashImage)
        val animator: ViewPropertyAnimator = splashImage.animate()
            .translationX(-150f)
            .setDuration(1500)

        animator.start()
    }

    private fun checkLoginState() {
        val currentUser = Firebase.auth.currentUser
        if (false) {
            val employeeNo = currentUser?.email.toString().replace("@doremi.com", "")

            loginIntent = Intent(this, MainActivity::class.java)

            viewModel.getUserInfo(employeeNo)
        } else {
            loginIntent = Intent(this, UserLoginActivity::class.java)
            startActivity(loginIntent)
        }

//        Handler().postDelayed({
//            loginIntent = Intent(this, UserLoginActivity::class.java)
//            startActivity(loginIntent)
//        }, 1500)
    }

    private fun setObserver() {
        viewModel.userInfo.observe(this) {
            val app = AppApplication.getInstance()
            app.setLoginInfo(it)

            startActivity(loginIntent)

            finish()
        }
    }
}
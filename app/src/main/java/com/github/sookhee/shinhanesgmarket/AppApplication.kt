package com.github.sookhee.shinhanesgmarket

import android.app.Application
import com.github.sookhee.domain.entity.User
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun setLoginInfo(user: User) {
        loginInfo = user
    }

    fun getLoginInfo(): User {
        return loginInfo
    }

    companion object {
        private lateinit var instance: AppApplication
        fun getInstance(): AppApplication = instance

        private var loginInfo = User()
    }
}
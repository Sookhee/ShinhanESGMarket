package com.github.sookhee.shinhanesgmarket.user

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.domain.entity.User
import com.github.sookhee.domain.usecase.GetUserInfoUseCase
import com.github.sookhee.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel @ViewModelInject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
) : ViewModel() {
    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User> = _userInfo

    private val _searchUserState = MutableLiveData<UserState>()
    val searchUseState: LiveData<UserState> = _searchUserState

    private val _registerUserState = MutableLiveData<UserState>()
    val registerUserState: LiveData<UserState> = _registerUserState

    fun getUserInfo(employeeNo: String) {
        _searchUserState.value = UserState.LOADING
        viewModelScope.launch {
            try {
                val userInfo = getUserInfoUseCase(employeeNo)

                _searchUserState.value = UserState.SUCCESS
                _userInfo.value = userInfo
            } catch (e: Exception) {
                Log.i(TAG, "getUserInfo exception : $e")
                _searchUserState.value = UserState.FAIL
            }
        }
    }

    fun registerUser(user: User) {
        viewModelScope.launch {
            _registerUserState.value = UserState.LOADING
            try {
                val isSuccess = registerUserUseCase(user)
                if (isSuccess) {
                    _registerUserState.value = UserState.SUCCESS
                } else {
                    _registerUserState.value = UserState.FAIL
                }
            } catch (e: Exception) {
                Log.i(TAG, "registerUser exception : $e")
                _registerUserState.value = UserState.FAIL
            }
        }
    }

    companion object{
        private val TAG = UserViewModel::class.simpleName
    }
}
package com.github.sookhee.shinhanesgmarket.user

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.domain.entity.User
import com.github.sookhee.domain.usecase.GetUserInfoUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel @ViewModelInject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {
    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User> = _userInfo

    private val _searchUserState = MutableLiveData<SearchUserState>()
    val searchUseState: LiveData<SearchUserState> = _searchUserState

    fun getUserInfo(employeeNo: String) {
        _searchUserState.value = SearchUserState.LOADING
        viewModelScope.launch {
            try {
                val userInfo = getUserInfoUseCase(employeeNo)

                _searchUserState.value = SearchUserState.SUCCESS
                _userInfo.value = userInfo
            } catch (e: Exception) {
                Log.i(TAG, "getUserInfo exception : $e")
                _searchUserState.value = SearchUserState.FAIL
            }
        }
    }

    companion object{
        private val TAG = UserViewModel::class.simpleName
    }
}
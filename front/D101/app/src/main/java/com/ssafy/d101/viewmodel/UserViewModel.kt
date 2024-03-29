package com.ssafy.d101.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.api.UserService
import com.ssafy.d101.model.User
import com.ssafy.d101.model.UserSubInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userService: UserService) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun updateUser(user: User) {
        viewModelScope.launch {
            _user.emit(user)
        }
    }

    fun clearUser() {
        viewModelScope.launch {
            _user.emit(null)
        }
    }

    private val _userSubInfo = MutableStateFlow<UserSubInfo?>(null)
    val userSubInfo = _userSubInfo.asStateFlow()

    fun getUserSubInfo() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                userService.getUserSubInfo()
            }
            if (response.isSuccessful) {
                _userSubInfo.value = response.body()
            }
        }
    }

    fun updateUserInfo(userSubInfo: UserSubInfo) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userService.updateUserSubInfo(userSubInfo)
            }
        }
    }
}
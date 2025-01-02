package com.example.messaging.Users.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.messaging.Users.data.User
import com.example.messaging.Users.domain.UserApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
@HiltViewModel
class UsersViewModel @Inject constructor(private val userApi: UserApi) : ViewModel() {

    val users = liveData(Dispatchers.IO) {
        try {
            // Try fetching real users
            emit(userApi.getUsers())
        } catch (e: Exception) {
            // On error, fallback to mock data
            emit(getMockUsers())
        }
    }

    private fun getMockUsers(): List<User> {
        return listOf(
            User(1, "Aman Jha","https://preview.keenthemes.com/metronic-v4/theme/assets/pages/media/profile/profile_user.jpg"),
            User(2, "Vivek Gupta","https://images.ctfassets.net/lh3zuq09vnm2/yBDals8aU8RWtb0xLnPkI/19b391bda8f43e16e64d40b55561e5cd/How_tracking_user_behavior_on_your_website_can_improve_customer_experience.png"),
            User(3,"Abhay Verma","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLe5PABjXc17cjIMOibECLM7ppDwMmiDg6Dw&s")
        )
    }
}
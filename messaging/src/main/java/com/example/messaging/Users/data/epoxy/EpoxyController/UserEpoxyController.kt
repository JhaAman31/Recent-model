package com.example.messaging.Users.data.epoxy.EpoxyController

import com.airbnb.epoxy.EpoxyController
import com.example.messaging.Constants
import com.example.messaging.Users.data.User
import com.example.messaging.Users.data.epoxy.EpoxyModel.UserEpoxyModel_


abstract class UserEpoxyController(private val onUserClick: (User) -> Unit) : EpoxyController() {

    private var users: List<User> = emptyList()

    fun setData(users: List<User>) {
        this.users = users
        requestModelBuild()
    }

    override fun buildModels() {
        users.forEach { user ->
            UserEpoxyModel_()
                .id(user.id)
                .apply {
                    if (user.id == Constants.CURRENT_USER_ID) {
                        name("${user.name} (You)")
                    } else {
                        name(user.name)
                    }
                }
                .profilePic(user.profilePic)
                .onClick { _ -> onUserClick(user) }
                .addTo(this)
        }
    }
}
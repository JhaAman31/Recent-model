package com.example.messaging.Users.presentation.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messaging.Chats.presentation.activity.ChattingActivity
import com.example.messaging.Constants
import com.example.messaging.Users.data.epoxy.EpoxyController.UserEpoxyController

import com.example.messaging.databinding.FragmentUserBinding
import com.example.messaging.Users.presentation.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private lateinit var userEpoxyController: UserEpoxyController

    private lateinit var userViewModel: UsersViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater)
        binding.userRecycler.layoutManager = LinearLayoutManager(requireContext())

        // Initializing ViewModel and EpoxyController
        userViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        userEpoxyController = object : UserEpoxyController({ user ->
            navigateToChat(user.id, user.name, user.profilePic)
        }) {}

        // Setting up RecyclerView
        binding.userRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.userRecycler.setController(userEpoxyController)

        // Observing all user data
        userViewModel.users.observe(viewLifecycleOwner) { users ->
            userEpoxyController.setData(users)
        }

        return binding.root
    }

    private fun navigateToChat(userId: Int, userName: String, userProfilePic: String) {
        val chatId = generateChatId(userId)
        val intent = Intent(requireContext(), ChattingActivity::class.java).apply {
            putExtra("otherUserName", userName)
            putExtra("otherUserPic", userProfilePic)
            putExtra("otherUserId",userId )
            putExtra("chatroomId", chatId)
        }
        startActivity(intent)

    }

    private fun generateChatId(userId: Int): String {
        return if (Constants.CURRENT_USER_ID < userId) {
            "${Constants.CURRENT_USER_ID}_$userId"
        } else {
            "${userId}_${Constants.CURRENT_USER_ID}"
        }
    }

}
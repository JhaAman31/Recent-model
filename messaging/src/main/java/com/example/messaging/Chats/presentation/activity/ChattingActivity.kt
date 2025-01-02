package com.example.messaging.Chats.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.messaging.Chats.data.Message
import com.example.messaging.Chats.data.epoxy.EpoxyController.EpoxyChatController
import com.example.messaging.Chats.presentation.viewmodel.ChatViewModel
import com.example.messaging.Constants.CURRENT_USER_ID
import com.example.messaging.R
import com.example.messaging.databinding.ActivityChattingBinding

class ChattingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChattingBinding

    private lateinit var name: String
//    private lateinit var id: String
    private lateinit var chatroomId: String
    private lateinit var profilePic: String
    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var epoxyController: EpoxyChatController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupEpoxyRecyclerView()
        observeMessages()
        setupSendMessageButton()
        setUpUserData()

        val webSocketUrl = "ws://your-websocket-url" // Replace with your WebSocket URL
        chatViewModel.connectWebSocket(webSocketUrl)
    }

    private fun setUpUserData() {

        name = intent.getStringExtra("otherUserName").toString()
        chatroomId = intent.getStringExtra("chatroomId").toString()
        profilePic = intent.getStringExtra("otherUserPic").toString()
        binding.userName.text = name
        Glide.with(this).load(profilePic).placeholder(R.drawable.ic_profile_circle)
            .into(binding.profilePic)
    }

    private fun setupEpoxyRecyclerView() {
        epoxyController = EpoxyChatController(CURRENT_USER_ID)
        binding.messageRecycler.setController(epoxyController)
    }

    private fun observeMessages() {
        chatViewModel.messages.observe(this, Observer { messages ->
            epoxyController.messages = messages
            epoxyController.requestModelBuild()
            binding.messageRecycler.scrollToPosition(messages.size - 1)
        })
    }

    private fun setupSendMessageButton() {
        binding.sendBtn.setOnClickListener {
            val content = binding.messageInput.text.toString()
            if (content.isNotBlank()) {
                val message = Message(
                    chatroomId = chatroomId, // Replace with actual chat room ID
                    senderId = CURRENT_USER_ID,
                    content = content,
                    timestamp = System.currentTimeMillis()
                )
                chatViewModel.sendMessage(message)
                binding.messageInput.text?.clear()
            } else {
                Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
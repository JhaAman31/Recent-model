package com.example.messaging.Chats.presentation.activity

import android.os.Bundle
import android.view.View
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
import com.example.messaging.Chats.domain.PeerToPeerChatRepository
import com.example.messaging.Chats.presentation.viewmodel.ChatViewModel
import com.example.messaging.Constants
import com.example.messaging.Constants.CURRENT_USER_ID
import com.example.messaging.R
import com.example.messaging.databinding.ActivityChattingBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChattingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChattingBinding

    private lateinit var otherUserName: String

    var otherUserId: Int = 0
    private lateinit var chatroomId: String
    private lateinit var otherUserPic: String

    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var epoxyController: EpoxyChatController

    @Inject
    lateinit var repository: PeerToPeerChatRepository

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
        MessageValidator(true)

        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setUpUserData()
        setupEpoxyRecyclerView()
        setupSendButton()

        chatViewModel.connectToWebSocket(Constants.CURRENT_USER_ID.toString())
        observeMessages()

    }

    private fun setUpUserData() {

        otherUserId = intent.getStringExtra("otherUserId")?.toInt() ?: 0
        otherUserName = intent.getStringExtra("otherUserName") ?: ""
        chatroomId = intent.getStringExtra("chatroomId") ?: ""
        otherUserPic = intent.getStringExtra("otherUserPic") ?: ""
        binding.userName.text = otherUserName
        Glide.with(this).load(otherUserPic).placeholder(R.drawable.ic_profile_circle)
            .into(binding.profilePic)
    }


    private fun setupEpoxyRecyclerView() {
        epoxyController =
            EpoxyChatController(currentUserId = Constants.CURRENT_USER_ID, otherUserPic)
        binding.messageRecycler.setController(epoxyController)
    }

    private fun setupSendButton() {

        if (binding.messageInput.text.isEmpty()) {
            MessageValidator(true)
        } else {
            MessageValidator(false)
        }

        binding.sendMessage.setOnClickListener {
            val messageContent = binding.messageInput.text.toString().trim()
            if (messageContent.isNotEmpty()) {
                chatViewModel.sendMessage(otherUserId.toString(), messageContent)
                addSentMessageToRecyclerView(messageContent)
                binding.messageInput.text?.clear()
            } else {
                MessageValidator(true)
                Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addSentMessageToRecyclerView(messageContent: String) {
        val message = Message(
            chatroomId = chatroomId, // Replace with actual chatroom ID
            senderId = Constants.CURRENT_USER_ID,
            content = messageContent,
            timestamp = System.currentTimeMillis()
        )
        epoxyController.messages = epoxyController.messages + message
        epoxyController.requestModelBuild()
        binding.messageRecycler.scrollToPosition(epoxyController.messages.size - 1)
    }

    private fun observeMessages() {
        chatViewModel.receivedMessages.observe(this, Observer { messages ->
            val receivedMessages = messages.map {
                Message(
                    chatroomId = chatroomId, // Replace with actual chatroom ID
                    senderId = otherUserId,
                    content = it.second,
                    timestamp = System.currentTimeMillis()
                )
            }
            epoxyController.messages = epoxyController.messages + receivedMessages
            epoxyController.requestModelBuild()
            binding.messageRecycler.scrollToPosition(epoxyController.messages.size - 1)
        })
    }

    fun MessageValidator(isEmpty: Boolean) {
        if (isEmpty) {
            binding.sendMessage.visibility = View.GONE
        } else {
            binding.sendMessage.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        chatViewModel.disconnect()
    }
}
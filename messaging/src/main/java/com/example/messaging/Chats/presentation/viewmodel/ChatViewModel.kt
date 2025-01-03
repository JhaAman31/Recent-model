package com.example.messaging.Chats.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messaging.Chats.data.Message
import com.example.messaging.Chats.domain.PeerToPeerChatRepository
import com.example.messaging.websockets.WebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject

//
//class ChatViewModel : ViewModel() {
//
//    private val _messages = MutableLiveData<List<Message>>()
//    val messages: LiveData<List<Message>> get() = _messages
//
//    private lateinit var webSocket: WebSocket
//    private val client = OkHttpClient()
//    private val webSocketClient = object : WebSocketClient("ChatViewModel") {
//        override fun onMessage(webSocket: WebSocket, text: String) {
//            super.onMessage(webSocket, text)
//            // Parse message received
//            val newMessage = parseMessage(text)
//            addMessage(newMessage)
//        }
//    }
//
//    init {
//        _messages.value = emptyList()
//    }
//
//    fun connectWebSocket(url: String) {
//        val request = Request.Builder().url(url).build()
//        webSocket = client.newWebSocket(request, webSocketClient)
//    }
//
//    fun sendMessage(message: Message) {
//        // Converting message to JSON
//        val messageJson = messageToJson(message)
//        webSocket.send(messageJson)
//        addMessage(message)
//    }
//
//    private fun addMessage(message: Message) {
//        val currentMessages = _messages.value.orEmpty()
//        _messages.postValue(currentMessages + message)
//    }
//
//    private fun parseMessage(json: String): Message {
//        // Parsing JSON into a Message object (use Gson or another JSON library)
//        return Message("1", 1, "Sample Content", System.currentTimeMillis())
//    }
//
//    private fun messageToJson(message: Message): String {
//        // Convert Message object to JSON
//        return """{
//            "chatroomId": "${message.chatroomId}",
//            "senderId": ${message.senderId},
//            "content": "${message.content}",
//            "timestamp": ${message.timestamp}
//        }"""
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        webSocket.close(WebSocketClient.NORMAL_CLOSURE_STATUS_CODE, null)
//    }
//}

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: PeerToPeerChatRepository
) : ViewModel() {

    private val _receivedMessages = MutableLiveData<List<Pair<String, String>>>()
    val receivedMessages: LiveData<List<Pair<String, String>>> = _receivedMessages

    fun connectToWebSocket(userId: String) {
        repository.connectWebSocket(userId)
    }

    fun sendMessage(toUser: String, message: String) {
        repository.sendMessage(toUser, message)
    }

    fun disconnect() {
        repository.disconnect()
    }

    fun fetchReceivedMessages() {
        _receivedMessages.value = repository.receivedMessages
    }
}

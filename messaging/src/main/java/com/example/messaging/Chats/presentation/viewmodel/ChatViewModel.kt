package com.example.messaging.Chats.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messaging.Chats.data.Message
import com.example.messaging.websockets.WebSocketClient
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class ChatViewModel : ViewModel() {

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    private lateinit var webSocket: WebSocket
    private val client = OkHttpClient()
    private val webSocketClient = object : WebSocketClient("ChatViewModel") {
        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            val newMessage = parseMessage(text) // Parse message received
            addMessage(newMessage)
        }
    }

    init {
        _messages.value = emptyList()
    }

    fun connectWebSocket(url: String) {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, webSocketClient)
    }

    fun sendMessage(message: Message) {
        val messageJson = messageToJson(message) // Convert message to JSON
        webSocket.send(messageJson)
        addMessage(message)
    }

    private fun addMessage(message: Message) {
        val currentMessages = _messages.value.orEmpty()
        _messages.postValue(currentMessages + message)
    }

    private fun parseMessage(json: String): Message {
        // Parse JSON into a Message object (use Gson or another JSON library)
        return Message("1", 1, "Sample Content", System.currentTimeMillis())
    }

    private fun messageToJson(message: Message): String {
        // Convert Message object to JSON
        return """{
            "chatroomId": "${message.chatroomId}",
            "senderId": ${message.senderId},
            "content": "${message.content}",
            "timestamp": ${message.timestamp}
        }"""
    }

    override fun onCleared() {
        super.onCleared()
        webSocket.close(WebSocketClient.NORMAL_CLOSURE_STATUS_CODE, null)
    }
}

package com.example.messaging.Chats.data

data class Message(val chatroomId:String,val senderId: Int, val content: String, val timestamp: Long)
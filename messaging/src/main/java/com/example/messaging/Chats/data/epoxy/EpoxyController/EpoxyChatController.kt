package com.example.messaging.Chats.data.epoxy.EpoxyController

import com.airbnb.epoxy.EpoxyController
import com.example.messaging.Chats.data.Message
import com.example.messaging.Chats.data.epoxy.EpoxyModel.ReceiveMsgEpoxyModel_
import com.example.messaging.Chats.data.epoxy.EpoxyModel.SentMsgEpoxyModel_
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EpoxyChatController(private val currentUserId: Int,private val profile:String) : EpoxyController() {

    var messages: List<Message> = emptyList()

    override fun buildModels() {
        messages.forEach { message ->
            if (message.senderId == currentUserId) {
                SentMsgEpoxyModel_()
                    .id(message.timestamp)
                    .message(message.content)
                    .addTo(this)
            } else {
                ReceiveMsgEpoxyModel_()
                    .id(message.timestamp)
                    .message(message.content)
                    .profile(profile)
                    .addTo(this)
            }
        }
    }

    private fun formatTimestamp(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault()) // 24-hour format (HH) and minutes (mm)
        val date = Date(timestamp) // Convert the timestamp to a Date object
        return dateFormat.format(date)
    }
}

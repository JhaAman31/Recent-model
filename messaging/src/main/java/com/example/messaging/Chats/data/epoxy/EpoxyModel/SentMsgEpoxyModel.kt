package com.example.messaging.Chats.data.epoxy.EpoxyModel

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.messaging.R
import com.example.messaging.databinding.ItemSentMessageBinding

@EpoxyModelClass
abstract class SentMsgEpoxyModel : EpoxyModelWithHolder<SentMsgEpoxyModel.ViewHolder>() {
    override fun getDefaultLayout(): Int {
        return R.layout.item_sent_message
    }

    @EpoxyAttribute
    lateinit var message: String


    @EpoxyAttribute
    lateinit var messageTime: String

    override fun bind(holder: ViewHolder) {
        holder.binding.message.text = message

    }

    class ViewHolder : EpoxyHolder() {
        lateinit var binding: ItemSentMessageBinding
        override fun bindView(itemView: View) {
            binding = ItemSentMessageBinding.bind(itemView)
        }
    }
}


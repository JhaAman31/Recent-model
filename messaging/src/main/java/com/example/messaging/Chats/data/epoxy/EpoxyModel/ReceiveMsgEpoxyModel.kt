package com.example.messaging.Chats.data.epoxy.EpoxyModel

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.example.messaging.R
import com.example.messaging.databinding.ItemReceiveMessageBinding


@EpoxyModelClass
abstract class ReceiveMsgEpoxyModel : EpoxyModelWithHolder<ReceiveMsgEpoxyModel.ViewHolder>() {

    override fun getDefaultLayout(): Int {
        return R.layout.item_receive_message
    }

    @EpoxyAttribute
    lateinit var message: String

    @EpoxyAttribute
    lateinit var profile: String

    @EpoxyAttribute
    lateinit var userName: String

    @EpoxyAttribute
    lateinit var messageTime: String

    override fun bind(holder: ViewHolder) {
        holder.binding.messageText.text = message
        holder.binding.timestamp.text = messageTime
//        holder.binding.senderName.text = userName

//        Glide.with(holder.binding.senderName).load(profile)
//            .placeholder(R.drawable.ic_profile_circle).into(holder.binding.profileImage)
    }

    class ViewHolder : EpoxyHolder() {
        lateinit var binding: ItemReceiveMessageBinding
        override fun bindView(itemView: View) {
            binding = ItemReceiveMessageBinding.bind(itemView)
        }
    }
}

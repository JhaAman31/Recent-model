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



    override fun bind(holder: ViewHolder) {
        holder.binding.message.text = message


        Glide.with(holder.binding.root.context).load(profile)
            .placeholder(R.drawable.ic_profile_circle).into(holder.binding.userProfilePic)
    }

    class ViewHolder : EpoxyHolder() {
        lateinit var binding: ItemReceiveMessageBinding
        override fun bindView(itemView: View) {
            binding = ItemReceiveMessageBinding.bind(itemView)
        }
    }
}

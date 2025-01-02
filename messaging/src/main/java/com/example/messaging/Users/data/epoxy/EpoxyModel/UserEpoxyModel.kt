package com.example.messaging.Users.data.epoxy.EpoxyModel

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.example.messaging.R
import com.example.messaging.databinding.ItemUserBinding

@EpoxyModelClass
abstract class UserEpoxyModel : EpoxyModelWithHolder<UserEpoxyModel.Holder>() {

    override fun getDefaultLayout(): Int {
        return R.layout.item_user
    }

    @EpoxyAttribute
    lateinit var name: String

    @EpoxyAttribute
    lateinit var profilePic: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onClick: View.OnClickListener


    override fun bind(holder: Holder) {

        holder.binding.userName.text = name

        Glide.with(holder.binding.userName.context).load(profilePic)
            .placeholder(R.drawable.ic_profile_circle)
            .into(holder.binding.userProfilePicture)

        holder.binding.root.setOnClickListener(onClick)

    }

    class Holder : com.airbnb.epoxy.EpoxyHolder() {
        lateinit var binding: ItemUserBinding
        override fun bindView(itemView: View) {
            binding = ItemUserBinding.bind(itemView)
        }
    }
}
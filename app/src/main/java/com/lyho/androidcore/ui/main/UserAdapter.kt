package com.lyho.androidcore.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lyho.androidcore.databinding.UserItemBinding
import com.lyho.androidcore.ui.common.BaseRecyclerAdapter
import com.lyho.androidcore.ui.common.BaseViewHolder
import com.lyho.androidcore.ui.common.RecyclerDiffUtilCallBack

class UserAdapter(
    diffUtil: RecyclerDiffUtilCallBack<User>
) : BaseRecyclerAdapter<User, UserViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        return UserViewHolder(
            parent.context,
            UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

}

class UserViewHolder(context: Context, binding: UserItemBinding) :
    BaseViewHolder<UserItemBinding, User>(context, binding) {

    override fun onBind(item: User) {
        binding.tvName.text = item.name
        binding.tvId.text = item.id.toString()
    }
}
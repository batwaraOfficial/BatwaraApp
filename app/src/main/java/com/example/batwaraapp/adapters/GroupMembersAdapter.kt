package com.example.batwaraapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.batwaraapp.databinding.ItemSelectUserBinding
import com.example.batwaraapp.datamodels.UserModel
import com.example.batwaraapp.utils.InterfaceUtils.uniClick

class GroupMembersAdapter : RecyclerView.Adapter<GroupMembersAdapter.UsersViewHolder>() {

    private var userList = ArrayList<UserModel>()

    private var onGroupClickListener: ((UserModel, Boolean, Int) -> Unit)? = null

    private var makeSelectable: Boolean = false

    fun setDataSet(userList: ArrayList<UserModel>, makeSelectable: Boolean) {
        this.userList = userList
        this.makeSelectable = makeSelectable
        notifyDataSetChanged()
    }

    fun setOnUserClickListener(onGroupClickListener: ((UserModel, Boolean, Int) -> Unit)?) {
        this.onGroupClickListener = onGroupClickListener
    }

    inner class UsersViewHolder(var binding: ItemSelectUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserModel, position: Int) {
            binding.user = user
            binding.isSelectable = makeSelectable
            binding.root.uniClick (true) {
                onGroupClickListener?.invoke(user, binding.selectUser.isChecked, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(ItemSelectUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(userList[position], position)
    }

    override fun getItemCount(): Int  = userList.size

}
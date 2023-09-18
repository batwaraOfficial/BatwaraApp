package com.example.batwaraapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.batwaraapp.databinding.ItemGroupBinding
import com.example.batwaraapp.databinding.ItemSelectUserBinding
import com.example.batwaraapp.datamodels.UserGroup
import com.example.batwaraapp.datamodels.UserModel
import com.example.batwaraapp.utils.InterfaceUtils.uniClick

class GroupMembersAdapter : RecyclerView.Adapter<GroupMembersAdapter.UsersViewHolder>() {

    private var userList = ArrayList<UserModel>()

    private var onGroupClickListener: ((UserModel) -> Unit)? = null

    private var makeSelectable: Boolean = false

    fun setDataSet(userList: ArrayList<UserModel>?, makeSelectable: Boolean) {
        userList?.let { this.userList = userList }
        this.makeSelectable = makeSelectable
        notifyDataSetChanged()
    }

    fun setOnUserClickListener(onGroupClickListener: ((UserModel) -> Unit)?) {
        this.onGroupClickListener = onGroupClickListener
    }

    inner class UsersViewHolder(var binding: ItemSelectUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserModel) {
            binding.user = user
            binding.isSelectable = makeSelectable
            binding.root.uniClick (true) {
                onGroupClickListener?.invoke(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(ItemSelectUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {

    }

    override fun getItemCount(): Int  = userList.size

}
package com.example.batwaraapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.batwaraapp.databinding.ItemGroupBinding
import com.example.batwaraapp.databinding.ItemLocalPersonBinding
import com.example.batwaraapp.datamodels.UserGroup
import com.example.batwaraapp.datamodels.UserModel
import com.example.batwaraapp.utils.InterfaceUtils.uniClick

class GroupsAdapter : RecyclerView.Adapter<GroupsAdapter.GroupViewHolder>() {

    private var groupList = ArrayList<UserGroup>()

    private var onGroupClickListener: ((UserGroup) -> Unit)? = null

    fun setDataSet(groupList: ArrayList<UserGroup>?) {
        groupList?.let { this.groupList = groupList }
        notifyDataSetChanged()
    }

    fun setOnGroupClickListener(onGroupClickListener: ((UserGroup) -> Unit)?) {
        this.onGroupClickListener = onGroupClickListener
    }

    inner class GroupViewHolder(var binding: ItemGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userGroup: UserGroup) {
            binding.group = userGroup
            var memList = ""
            for(i in 0 until userGroup.groupMembers?.size!!) {
                if(i == userGroup.groupMembers?.size!! -1) {
                    memList += " and ${userGroup.groupMembers!![i].name}."
                    break
                }
                memList += userGroup.groupMembers!![i].name + ","
            }
            binding.membersList.text = memList
            binding.root.uniClick (true) {
                onGroupClickListener?.invoke(userGroup)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groupList[position])
    }

    override fun getItemCount(): Int  = groupList.size

}
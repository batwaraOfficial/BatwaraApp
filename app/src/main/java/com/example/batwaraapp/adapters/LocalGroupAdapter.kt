package com.example.batwaraapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.batwaraapp.databinding.ItemLocalPersonBinding
import com.example.batwaraapp.datamodels.UserModel
import com.example.batwaraapp.utils.InterfaceUtils.uniClick

class LocalGroupAdapter : RecyclerView.Adapter<LocalGroupAdapter.GroupMemberViewHolder>() {

    private var memberList = ArrayList<UserModel>()

    private var onRemoveClickListener: ((UserModel) -> Unit)? = null

    fun setDataSet(memberList: ArrayList<UserModel>?) {
        memberList?.let { this.memberList = memberList }
        notifyDataSetChanged()
    }

    fun setOnRemoveClickListener(onRemoveClickListener: ((UserModel) -> Unit)?) {
        this.onRemoveClickListener = onRemoveClickListener
    }

    inner class GroupMemberViewHolder(var binding: ItemLocalPersonBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMemberViewHolder {
        return GroupMemberViewHolder(ItemLocalPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GroupMemberViewHolder, position: Int) {
        holder.binding.member = memberList[position]
        holder.binding.removeMember.uniClick { onRemoveClickListener?.invoke(memberList[position]) }
    }

    override fun getItemCount(): Int  = memberList.size

}
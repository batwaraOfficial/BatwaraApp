package com.example.batwaraapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.batwaraapp.databinding.ItemUserTagBinding
import com.example.batwaraapp.datamodels.UserTag
import com.example.batwaraapp.utils.InterfaceUtils.uniClick

class GroupsTagsAdapter : RecyclerView.Adapter<GroupsTagsAdapter.GroupTagsViewHolder>() {

    private var tagsList = ArrayList<UserTag>()

    private var onTagClickListener: ((UserTag) -> Unit)? = null

    fun setDataSet(tagsList: ArrayList<UserTag>?) {
        tagsList?.let { this.tagsList = tagsList }
        notifyDataSetChanged()
    }

    fun setOnTagClickListener(onTagClickListener: ((UserTag) -> Unit)?) {
        this.onTagClickListener = onTagClickListener
    }

    inner class GroupTagsViewHolder(var binding: ItemUserTagBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupTagsViewHolder {
        return GroupTagsViewHolder(ItemUserTagBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GroupTagsViewHolder, position: Int) {
        holder.binding.tag = tagsList[position]
        holder.binding.tagButton.uniClick {
            onTagClickListener?.invoke(tagsList[position])
        }
    }

    override fun getItemCount(): Int  = tagsList.size

}
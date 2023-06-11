package com.example.batwaraapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.batwaraapp.datamodels.UserModel
import com.example.batwaraapp.datamodels.UserTag

class LocalSplitGroupViewModel : ViewModel() {

    var currentName = MutableLiveData<String?>("")
    var errorString = MutableLiveData<String?>(null)
    var currentMemberList = MutableLiveData<ArrayList<UserModel>>(ArrayList())
    var tagsList = MutableLiveData<ArrayList<UserTag>>(ArrayList())

    fun removeMember(member: UserModel) {
        var tempList = currentMemberList.value ?: ArrayList()
        for (i in 0 until tempList.size) {
            if (tempList.get(i).username == member.username) {
                tempList.removeAt(i)
                break
            }
        }
        currentMemberList.postValue(tempList)
    }

    fun addMember(member: UserModel) {
        var tempList = currentMemberList.value ?: ArrayList()
        tempList.add(member)
        currentMemberList.postValue(tempList)
    }

    fun selectTag(tag: UserTag) {
        var tempList = tagsList.value ?: ArrayList()
        if (tempList != null) {
            for(i in tempList) {
                if(i.tag == tag.tag) {
                    i.isSelected = !i.isSelected
                    break
                }
            }
        }
        tagsList.postValue(tempList)
    }
}
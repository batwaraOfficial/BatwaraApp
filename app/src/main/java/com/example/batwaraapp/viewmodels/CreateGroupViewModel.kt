package com.example.batwaraapp.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.batwaraapp.datamodels.UserGroup
import com.example.batwaraapp.datamodels.UserModel
import com.example.batwaraapp.datamodels.UserTag
import com.example.batwaraapp.utils.EmailForUse
import com.example.batwaraapp.utils.SharedPrefsKeys
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class CreateGroupViewModel : ViewModel() {
    /**
     * For CreateLocalSplitGroup Activity.
     * */
    var errorString = MutableLiveData<String?>(null)
    var currentName = MutableLiveData<String?>("")
    var currentMemberList = MutableLiveData<ArrayList<UserModel>>(ArrayList())
    var currentTags = MutableLiveData<ArrayList<UserTag>>(ArrayList())
    /**
     * For GroupsScreen Activity.
     * */
    var groupsData = MutableLiveData<ArrayList<UserGroup>>(ArrayList())
    /**
     * General Use.
     * */
    private val db = Firebase.firestore
    fun addGroupData(userKey:String, groupName: String, groupDescription: String, onSuccessListener: () -> Unit, onFailure: (Exception?) -> Unit) {
        try {
            db.collection("user").document(userKey).get().addOnSuccessListener {
                var currGroupArray = it.toObject<UserModel>()?.userGroups
                currGroupArray?.add(
                    UserGroup(
                        groupName = groupName,
                        groupDescription = groupDescription,
                        groupMembers = currentMemberList.value,
                        tags = currentTags.value,
                        expenses = ArrayList()
                    )
                )

                db.collection("user").document(userKey).update("userGroups", currGroupArray).addOnSuccessListener {
                        onSuccessListener.invoke()
                }.addOnFailureListener {
                    onFailure.invoke(it)
                }
            }
        } catch (e: Exception) {
            onFailure.invoke(e)
        }
    }
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
        var tempList = currentTags.value ?: ArrayList()
        if (tempList != null) {
            for(i in tempList) {
                if(i.tag == tag.tag) {
                    i.isSelected = !i.isSelected
                    break
                }
            }
        }
        currentTags.postValue(tempList)
    }
    fun getGroupData(userKey: String, onSuccessListener: () -> Unit, onFailure: (Exception?) -> Unit) {
        groupsData.value = ArrayList()
        try {
            db.collection("user").document(userKey).get().addOnSuccessListener {
                groupsData.value = it.toObject<UserModel>()?.userGroups ?: ArrayList()
                onSuccessListener.invoke()
            }.addOnFailureListener { e ->
                onFailure.invoke(e)
            }
        } catch (e: Exception) {
            onFailure.invoke(e)
        }
    }
}


package com.example.batwaraapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.batwaraapp.datamodels.UserGroup

class GroupsViewModel : ViewModel() {

    var groupsList = MutableLiveData<ArrayList<UserGroup>>(ArrayList())

    fun setUpGroups() {
        groupsList.value = arrayListOf(UserGroup())
    }

}
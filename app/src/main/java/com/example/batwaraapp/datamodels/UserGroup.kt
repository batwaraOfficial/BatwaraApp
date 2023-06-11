package com.example.batwaraapp.datamodels

data class UserGroup (
    var groupName: String? = null,
    var groupDescription: String? = null,
    var tags: ArrayList<UserTag>? = null
)
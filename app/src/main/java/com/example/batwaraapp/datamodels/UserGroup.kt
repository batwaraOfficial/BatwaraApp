package com.example.batwaraapp.datamodels

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UserGroup (
    var groupId: Int? = null,
    var groupName: String? = null,
    var groupDescription: String? = "No Description.",
    var expenses: ArrayList<Expense>? = null,
    var groupMembers: ArrayList<UserModel>? = null,
    var tags: ArrayList<UserTag>? = null
): Parcelable
package com.example.batwaraapp.datamodels

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UserGroup (
    /**
     * Include following in firebase.
     * @param groupName
     * @param groupDescription
     * @param expenses
     * @param groupMembers
     * @param tags
     * */
    var groupId: Int? = null,
    var groupName: String? = null,
    var groupDescription: String? = "No Group Description.",
    var expenses: ArrayList<Expense>? = ArrayList(),
    var groupMembers: ArrayList<UserModel>? = ArrayList(),
    var tags: ArrayList<UserTag>? = null
): Parcelable
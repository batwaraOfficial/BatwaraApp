package com.example.batwaraapp.datamodels

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UserModel(
    /**
     * Include following in firebase.
     * @param userId
     * @param name
     * @param userGroups
     * */
    var userId: Int? = null,
    var username: String? = null,
    var name: String? = null,
    var userGroups: ArrayList<UserGroup>? = ArrayList(),
    var number: String? = null,
    var isSelected: Boolean = false
) : Parcelable

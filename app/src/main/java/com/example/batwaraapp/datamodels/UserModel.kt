package com.example.batwaraapp.datamodels

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UserModel(
    val userId: Int? = null,
    val username: String? = null,
    val name: String? = null
) : Parcelable

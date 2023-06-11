package com.example.batwaraapp.datamodels

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UserTag (
    var tag: String? = null,
    var isSelected: Boolean = false
) : Parcelable
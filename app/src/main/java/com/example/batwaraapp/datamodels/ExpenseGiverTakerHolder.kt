package com.example.batwaraapp.datamodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExpenseGiverTakerHolder(
    var position: Int? = null,
    var user: UserModel? = null
) : Parcelable

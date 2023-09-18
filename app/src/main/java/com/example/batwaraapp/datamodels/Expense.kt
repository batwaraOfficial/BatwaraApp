package com.example.batwaraapp.datamodels

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Expense (
    var expenseId: Int? = null,
    var groupId: String? = null,
    var transactionAmount: Int? = null,
    var expenseGiverId: Int? = null,
    var expenseGiverName: String? = null,
    var expenseTakerId: Int? = null,
    var expenseTakerName: String? = null,
    var expenseDescription: String? = null
): Parcelable
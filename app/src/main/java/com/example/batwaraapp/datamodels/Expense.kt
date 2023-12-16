package com.example.batwaraapp.datamodels

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Expense (
    /**
     * Include following in firebase.
     * @param expenseDescription
     * @param transactionAmount
     * @param expenseGiverId
     * @param expenseGiverName
     * @param expenseTakerIds
     * @param expenseTakerNames
     * */
    var transactionAmount: Int? = null, // amount
    var expenseGiverId: Int? = null, // index of giver
    var expenseGiverName: String? = null,
    var expenseTakerIds: ArrayList<Int>? = null, // index of takers
    var expenseTakerNames: ArrayList<String>? = null,
    var expenseDescription: String? = null
): Parcelable
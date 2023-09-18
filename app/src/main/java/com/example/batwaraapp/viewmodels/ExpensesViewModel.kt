package com.example.batwaraapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.batwaraapp.datamodels.Expense
import com.example.batwaraapp.utils.StaticHardData

class ExpensesViewModel : ViewModel() {

    var expensesList = MutableLiveData<ArrayList<Expense>>(ArrayList())

    fun setUpGroups() {
        expensesList.value = StaticHardData.hardExpenses
    }

}
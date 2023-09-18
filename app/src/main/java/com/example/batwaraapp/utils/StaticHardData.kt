package com.example.batwaraapp.utils

import com.example.batwaraapp.datamodels.Expense
import com.example.batwaraapp.datamodels.UserGroup
import com.example.batwaraapp.datamodels.UserModel

object StaticHardData {

    var hardExpenses = arrayListOf(Expense(
        transactionAmount = 200,
        expenseGiverName = "Vinayak",
        expenseTakerName = "Sahil"
    ), Expense(
        transactionAmount = 45,
        expenseGiverName = "Tarun",
        expenseTakerName = "Sahil"
    ), Expense(
        transactionAmount = 20,
        expenseGiverName = "Tarun",
        expenseTakerName = "Vinayak"
    ))

    var hardGroups = arrayListOf(UserGroup(
        groupName = "Project Costing",
        groupDescription = "Servers costing, PlayStore costing.",
        expenses = hardExpenses
    ))

    var hardGroupMembers = arrayListOf(UserModel(
        name = "Vinayak",
        userGroups = hardGroups,
        number = "8506060147"
    ), UserModel(
        name = "Sahil",
        userGroups = hardGroups,
        number = "8507890147"
    ), UserModel(
        name = "Tarun",
        userGroups = hardGroups,
        number = "8786060147"
    ))

}
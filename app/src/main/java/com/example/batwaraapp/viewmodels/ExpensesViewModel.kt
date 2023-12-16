package com.example.batwaraapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.batwaraapp.datamodels.Expense
import com.example.batwaraapp.datamodels.ExpenseGiverTakerHolder
import com.example.batwaraapp.datamodels.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ExpensesViewModel : ViewModel() {

    /**
     * For ExpensesActivity.
     * */
    var expensesList = MutableLiveData<ArrayList<Expense>?>(ArrayList())
    var groupMembers = MutableLiveData<ArrayList<UserModel>>(ArrayList())
    private val db = Firebase.firestore
    var groupName: String? = null

    /**
     * For AddExpenseActivity.
     * */
    var expenseGiver = MutableLiveData<ExpenseGiverTakerHolder>()
    var expenseTakers = MutableLiveData<ArrayList<ExpenseGiverTakerHolder>>(ArrayList())

    fun setData(userKey:String, onSuccessListener: () -> Unit, onFailure: (Exception?) -> Unit) {
        try {
            db.collection("user").document(userKey).get().addOnSuccessListener {
                var memList = it.toObject<UserModel>()
                expensesList.value = memList?.userGroups?.firstOrNull{ userGroup ->  userGroup.groupName == groupName}?.expenses
                onSuccessListener.invoke()
            }.addOnFailureListener { e ->
                onFailure.invoke(e)
            }
        } catch (e: Exception) {
            onFailure.invoke(e)
        }
    }

    fun clearExpenses(userKey:String, onSuccessListener: () -> Unit, onFailure: (Exception?) -> Unit) {
        try {
            db.collection("user").document(userKey).get().addOnSuccessListener {
                var memList = it.toObject<UserModel>()
                expensesList.value = ArrayList()
                for(i in 0 until memList?.userGroups?.size!!) {
                    if(memList.userGroups!![i].groupName == groupName) {
                        memList.userGroups!![i].expenses = ArrayList()
                        break
                    }
                }
                db.collection("user").document(userKey).update("userGroups", memList.userGroups).addOnSuccessListener {
                    onSuccessListener.invoke()
                }.addOnFailureListener {
                    onFailure.invoke(it)
                }
                onSuccessListener.invoke()
            }.addOnFailureListener { e ->
                onFailure.invoke(e)
            }

        } catch (e: Exception) {
            onFailure.invoke(e)
        }
    }

    fun addExpense(userKey:String, expense: Expense, onSuccessListener: () -> Unit, onFailure: (Exception?) -> Unit) {
        try {
            db.collection("user").document(userKey).get().addOnSuccessListener {
                var memList = it.toObject<UserModel>()
                for(i in 0 until memList?.userGroups?.size!!) {
                    if(memList.userGroups!![i].groupName == groupName) {
                        memList.userGroups!![i].expenses?.add(expense)
                        break
                    }
                }

                db.collection("user").document(userKey).update("userGroups", memList.userGroups).addOnSuccessListener {
                    onSuccessListener.invoke()
                }.addOnFailureListener {
                    onFailure.invoke(it)
                }
                onSuccessListener.invoke()
            }.addOnFailureListener { e ->
                onFailure.invoke(e)
            }

        } catch (e: Exception) {
            onFailure.invoke(e)
        }
    }
}
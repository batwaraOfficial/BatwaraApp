package com.example.batwaraapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.batwaraapp.R
import com.example.batwaraapp.adapters.GroupMembersAdapter
import com.example.batwaraapp.databinding.ActivityAddExpenseScreenBinding
import com.example.batwaraapp.databinding.SelectGroupMembersDialogBinding
import com.example.batwaraapp.datamodels.Expense
import com.example.batwaraapp.datamodels.ExpenseGiverTakerHolder
import com.example.batwaraapp.datamodels.UserGroup
import com.example.batwaraapp.utils.EmailForUse
import com.example.batwaraapp.utils.InterfaceUtils.uniClick
import com.example.batwaraapp.utils.SharedPrefsKeys
import com.example.batwaraapp.viewmodels.ExpensesViewModel

class AddExpenseScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAddExpenseScreenBinding
    val currUserGroup: UserGroup? by lazy { intent.getParcelableExtra(USER_GROUP) as UserGroup? }
    private val vm: ExpensesViewModel by lazy {
        ViewModelProvider(this).get(ExpensesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_expense_screen)
        supportActionBar?.hide()
        /**
         * User Key common in all activities for doing DB operations
         * */
        val sharedPreferences: SharedPreferences? = this.getSharedPreferences(SharedPrefsKeys.MAIN_PREF_KEY, MODE_PRIVATE)
        val authPrefKey = sharedPreferences?.getString(SharedPrefsKeys.AUTH_PREF_KEY, "")
        val userKey = authPrefKey?.let { EmailForUse.getFirstPartEmail(it) }

        vm.groupName = currUserGroup?.groupName
        binding.toolbar.backButton.uniClick(true) {
            onBackPressed()
        }
        binding.giverCard.uniClick(true) {showDialog(false)}
        binding.takerCard.uniClick(true) {showDialog(true)}
        binding.saveButton.uniClick(true) {
            if(binding.amountInput.text.isNullOrEmpty() || binding.descriptionInput.text.isNullOrEmpty() || vm.expenseGiver.value == null || vm.expenseTakers.value.isNullOrEmpty()) {
                Toast.makeText(this, "No field should be empty.", Toast.LENGTH_SHORT).show()
            } else {
                val amount = binding.amountInput.text.toString().toInt()
                val description = binding.descriptionInput.text.toString()
                var expenseTakerIds = ArrayList<Int>()
                var expenseTakerNames = ArrayList<String>()
                var expenseGiverId: Int? = null
                var expenseGiverName: String? = null

                expenseGiverName = vm.expenseGiver.value?.user?.name
                expenseGiverId = vm.expenseGiver.value?.position

                for(holder in vm.expenseTakers.value!!) {
                    holder.position?.let { expenseTakerIds.add(it) }
                    holder.user?.name?.let { expenseTakerNames.add(it) }
                }

                if(expenseTakerIds.isNullOrEmpty() || expenseTakerNames.isNullOrEmpty() || expenseGiverId == null || expenseGiverName == null) {
                    Toast.makeText(this, "No field should be empty.", Toast.LENGTH_SHORT).show()
                } else {
                    if (userKey != null) {
                        vm.addExpense(userKey, Expense(transactionAmount = amount, expenseDescription = description, expenseGiverId = expenseGiverId, expenseGiverName = expenseGiverName, expenseTakerNames = expenseTakerNames, expenseTakerIds = expenseTakerIds), {
                            finish()
                        }, {
                            Toast.makeText(this, "${it?.message}", Toast.LENGTH_SHORT).show()
                        })
                    }
                }
            }
        }
    }

    private fun showDialog(makeSelectable: Boolean) {
        val _binding = SelectGroupMembersDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this).setView(_binding.root).create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        val _adapter = GroupMembersAdapter()

        var selectedUsers = ArrayList<ExpenseGiverTakerHolder>()

        _adapter.setOnUserClickListener { user, isSelected, position ->
            if(makeSelectable==true) {
                if(isSelected) {
                    selectedUsers.add(ExpenseGiverTakerHolder(position, user))
                } else {
                    selectedUsers.remove(ExpenseGiverTakerHolder(position, user))
                }
            } else {
                vm.expenseGiver.value = ExpenseGiverTakerHolder(position = position, user = user)
                dialog.dismiss()
            }
        }
        _binding.recyclerView.adapter = _adapter
        _adapter.setDataSet(currUserGroup?.groupMembers ?: ArrayList(), makeSelectable)
        _binding.doneButton.uniClick {
            if(makeSelectable) {
                vm.expenseTakers.value = selectedUsers
            } else {
                dialog.dismiss()
            }
            dialog.dismiss()
        }

        dialog.show()
    }
    data class LaunchSet(
        var currGroup: UserGroup? = null
    )

    companion object : ActivityResultContract<LaunchSet?, Boolean>() {
        val USER_GROUP = "user_g"
        override fun createIntent(context: Context, params: LaunchSet?): Intent {
            val intent = Intent(context, AddExpenseScreen::class.java).apply {
                putExtra(USER_GROUP, params?.currGroup)
            }
            return intent
        }
        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return resultCode == RESULT_OK
        }
    }
}
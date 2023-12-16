package com.example.batwaraapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.batwaraapp.R
import com.example.batwaraapp.adapters.ExpensesAdapter
import com.example.batwaraapp.databinding.ActivityGroupExpensesScreenBinding
import com.example.batwaraapp.databinding.DialogSimplifiedBinding
import com.example.batwaraapp.datamodels.UserGroup
import com.example.batwaraapp.utils.EmailForUse
import com.example.batwaraapp.utils.InterfaceUtils.uniClick
import com.example.batwaraapp.utils.SharedPrefsKeys
import com.example.batwaraapp.utils.TransactionReductionUtil
import com.example.batwaraapp.viewmodels.ExpensesViewModel

class GroupExpensesScreen : AppCompatActivity() {

    private lateinit var binding: ActivityGroupExpensesScreenBinding

    lateinit var adapter: ExpensesAdapter
    private val createAddExpenseScreen = registerForActivityResult(AddExpenseScreen) {}
    private var expenseSimplifier: TransactionReductionUtil = TransactionReductionUtil()
    val currUserGroup: UserGroup? by lazy { intent.getParcelableExtra(USER_GROUP) as UserGroup? }
    private val vm: ExpensesViewModel by lazy {
        ViewModelProvider(this).get(ExpensesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_expenses_screen)
        supportActionBar?.hide()

        /**
         * User Key common in all activities for doing DB operations
         * */
        val sharedPreferences: SharedPreferences? = this.getSharedPreferences(SharedPrefsKeys.MAIN_PREF_KEY, MODE_PRIVATE)
        val authPrefKey = sharedPreferences?.getString(SharedPrefsKeys.AUTH_PREF_KEY, "")
        val userKey = authPrefKey?.let { EmailForUse.getFirstPartEmail(it) }

        binding.toolbar.backButton.uniClick(true) {
            onBackPressed()
        }
        fillGroupMembers()
        var grpInfoText = ""
        grpInfoText += "Expenses : ${currUserGroup?.expenses?.size}\nTags: "
        currUserGroup?.tags?.let {
            for(i in it) {
                if(i.isSelected) grpInfoText += i.tag
            }
        }
        binding.groupInfo.text = grpInfoText

        adapter = ExpensesAdapter()

        adapter.setExpenseRemoveListener {

        }

        binding.recyclerView.adapter = adapter

        vm.expensesList.observe(this) {
            adapter.setDataSet(it)
        }

        vm.expensesList.value = currUserGroup?.expenses

        binding.addExpense.uniClick(true) {
            createAddExpenseScreen.launch(AddExpenseScreen.LaunchSet(currUserGroup))
        }

        binding.clearExpenses.uniClick(true) {
            if (userKey != null) {
                vm.clearExpenses(userKey, {} , {
                    Toast.makeText(this, "${it?.message}", Toast.LENGTH_LONG).show()
                })
            }
        }

        vm.expensesList.observe(this) {
            if(it != null) {
                binding.simplifyExpenses.uniClick {
                    var peopleList = ArrayList<String>()
                    for(member in vm.groupMembers.value!!) {
                        member.name?.let { peopleList.add(it) }
                    }

                    if(vm.expensesList.value != null) {
                        vm.expensesList.value?.let {
                            val simplifiedExpense = expenseSimplifier.createGraphForDebts(peopleList ,it)
                            Log.d("kkg-before", "onCreate: ")
                            showDialog(simplifiedExpense)
                            Log.d("kkg-after", "onCreate: ")
                        }
                    } else {
                        Toast.makeText(this, "No Expenses.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showDialog(text: String) {
        val _binding = DialogSimplifiedBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this).setView(_binding.root).create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        _binding.simplifiedText = text
        dialog.show()
    }

    private fun fillGroupMembers() {
        vm.groupMembers.value = currUserGroup?.groupMembers
    }

    data class LaunchSet(
        var userGroup: UserGroup? = null
    )

    companion object : ActivityResultContract<LaunchSet?, Boolean>() {
        val USER_GROUP = "user_group"
        override fun createIntent(context: Context, params: LaunchSet?): Intent {
            val intent = Intent(context, GroupExpensesScreen::class.java).apply {
                putExtra(USER_GROUP, params?.userGroup)
            }
            return intent
        }
        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return resultCode == RESULT_OK
        }
    }
}
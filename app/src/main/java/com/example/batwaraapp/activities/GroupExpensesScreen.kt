package com.example.batwaraapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.batwaraapp.R
import com.example.batwaraapp.adapters.ExpensesAdapter
import com.example.batwaraapp.databinding.ActivityCreateLocalSplitGroupBinding
import com.example.batwaraapp.databinding.ActivityGroupExpensesScreenBinding
import com.example.batwaraapp.utils.InterfaceUtils.uniClick
import com.example.batwaraapp.viewmodels.CreateGroupViewModel
import com.example.batwaraapp.viewmodels.ExpensesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class GroupExpensesScreen : AppCompatActivity() {

    private lateinit var binding: ActivityGroupExpensesScreenBinding

    lateinit var adapter: ExpensesAdapter
    private val createAddExpenseScreen = registerForActivityResult(AddExpenseScreen) {}

    private val vm: ExpensesViewModel by lazy {
        ViewModelProvider(this).get(ExpensesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_expenses_screen)
        supportActionBar?.hide()

        adapter = ExpensesAdapter()
        adapter.setExpenseRemoveListener {

        }
        binding.recyclerView.adapter = adapter
        vm.expensesList.observe(this) {
            adapter.setDataSet(it)
        }
        binding.addExpense.uniClick(true) {
            createAddExpenseScreen.launch(AddExpenseScreen.LaunchSet())
        }
        /**
         * Hard-Coded.
         * */
        lifecycleScope.launch(Dispatchers.IO) {
            delay(1000)
            vm.setUpGroups()
        }
    }

    data class LaunchSet(
        val groupId: Int? = null
    )

    companion object : ActivityResultContract<LaunchSet?, Boolean>() {
        override fun createIntent(context: Context, params: LaunchSet?): Intent {
            val intent = Intent(context, GroupExpensesScreen::class.java).apply {}
            return intent
        }
        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return resultCode == RESULT_OK
        }
    }
}
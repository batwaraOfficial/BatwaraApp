package com.example.batwaraapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.databinding.DataBindingUtil
import com.example.batwaraapp.R
import com.example.batwaraapp.adapters.GroupsAdapter
import com.example.batwaraapp.databinding.ActivityGroupsScreenBinding
import com.example.batwaraapp.utils.StaticHardData

class GroupsScreen : AppCompatActivity() {

    lateinit var binding: ActivityGroupsScreenBinding
    lateinit var adapter: GroupsAdapter
    private val createGroupExpenseScreen = registerForActivityResult(GroupExpensesScreen) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_groups_screen)
        supportActionBar?.hide()
        adapter = GroupsAdapter()
        adapter.setOnGroupClickListener {
            createGroupExpenseScreen.launch(GroupExpensesScreen.LaunchSet())
        }
        binding.recyclerView.adapter = adapter
        adapter.setDataSet(StaticHardData.hardGroups)
    }

    data class LaunchSet(
        val userId: Int? = null
    )

    companion object : ActivityResultContract<LaunchSet?, Boolean>() {
        override fun createIntent(context: Context, params: LaunchSet?): Intent {
            val intent = Intent(context, GroupsScreen::class.java).apply {}
            return intent
        }
        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return resultCode == RESULT_OK
        }
    }
}
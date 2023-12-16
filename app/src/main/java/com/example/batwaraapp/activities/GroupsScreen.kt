package com.example.batwaraapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.batwaraapp.R
import com.example.batwaraapp.adapters.GroupsAdapter
import com.example.batwaraapp.databinding.ActivityGroupsScreenBinding
import com.example.batwaraapp.utils.EmailForUse
import com.example.batwaraapp.utils.SharedPrefsKeys
import com.example.batwaraapp.viewmodels.CreateGroupViewModel

class GroupsScreen : AppCompatActivity() {

    lateinit var binding: ActivityGroupsScreenBinding
    lateinit var adapter: GroupsAdapter
    private val createGroupExpenseScreen = registerForActivityResult(GroupExpensesScreen) {}
    private val vm: CreateGroupViewModel by lazy {
        ViewModelProvider(this).get(CreateGroupViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_groups_screen)
        supportActionBar?.hide()

        /**
         * User Key common in all activities for doing DB operations
         * */
        val sharedPreferences: SharedPreferences? = getSharedPreferences(SharedPrefsKeys.MAIN_PREF_KEY, MODE_PRIVATE)
        val authPrefKey = sharedPreferences?.getString(SharedPrefsKeys.AUTH_PREF_KEY, "")
        val userKey = authPrefKey?.let { EmailForUse.getFirstPartEmail(it) }

        adapter = GroupsAdapter()
        adapter.setOnGroupClickListener {
            createGroupExpenseScreen.launch(GroupExpensesScreen.LaunchSet(it))
        }
        binding.recyclerView.adapter = adapter

        vm.groupsData.observe(this) {
            adapter.setDataSet(it)
        }
        if (userKey != null) {
            vm.getGroupData(userKey, {} , {
                Toast.makeText(this, "${it?.message}", Toast.LENGTH_SHORT).show()
            })
        }
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
package com.example.batwaraapp.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.batwaraapp.Constants
import com.example.batwaraapp.R
import com.example.batwaraapp.adapters.GroupMembersAdapter
import com.example.batwaraapp.adapters.GroupsTagsAdapter
import com.example.batwaraapp.databinding.ActivityAddExpenseScreenBinding
import com.example.batwaraapp.databinding.ActivityGroupExpensesScreenBinding
import com.example.batwaraapp.databinding.GroupDescriptionWindowBinding
import com.example.batwaraapp.databinding.SelectGroupMembersDialogBinding
import com.example.batwaraapp.datamodels.UserTag
import com.example.batwaraapp.utils.InterfaceUtils.uniClick
import com.example.batwaraapp.utils.StaticHardData

class AddExpenseScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAddExpenseScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_expense_screen)
        supportActionBar?.hide()

        binding.giverCard.uniClick(true) {showDialog(false)}
        binding.takerCard.uniClick(true) {showDialog(true)}
    }

    private fun showDialog(makeSelectable: Boolean) {
        val _binding = SelectGroupMembersDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this).setView(_binding.root).create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        val _adapter = GroupMembersAdapter()
        _adapter.setOnUserClickListener {

        }
        _binding.recyclerView.adapter = _adapter
        _adapter.setDataSet(StaticHardData.hardGroupMembers, makeSelectable)
        _binding.doneButton.uniClick {
            onBackPressed()
            dialog.dismiss()
        }

        dialog.show()
    }
    data class LaunchSet(
        val groupId: Int? = null
    )

    companion object : ActivityResultContract<LaunchSet?, Boolean>() {
        override fun createIntent(context: Context, params: LaunchSet?): Intent {
            val intent = Intent(context, AddExpenseScreen::class.java).apply {}
            return intent
        }
        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return resultCode == RESULT_OK
        }
    }
}
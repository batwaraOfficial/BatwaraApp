package com.example.batwaraapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.batwaraapp.R
import com.example.batwaraapp.databinding.ActivityCreateLocalSplitGroupBinding
import com.example.batwaraapp.databinding.ActivityGroupExpensesScreenBinding

class GroupExpensesScreen : AppCompatActivity() {

    private lateinit var binding: ActivityGroupExpensesScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_expenses_screen)
    }
}
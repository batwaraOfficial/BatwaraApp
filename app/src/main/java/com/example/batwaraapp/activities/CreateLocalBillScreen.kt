package com.example.batwaraapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.batwaraapp.R
import com.example.batwaraapp.databinding.ActivityCreateLocalBillScreenBinding

class CreateLocalBillScreen : AppCompatActivity() {

    private lateinit var binding: ActivityCreateLocalBillScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_local_bill_screen)

    }
}
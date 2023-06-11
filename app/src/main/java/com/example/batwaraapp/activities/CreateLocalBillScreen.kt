package com.example.batwaraapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.batwaraapp.R
import com.example.batwaraapp.databinding.ActivityCreateLocalBillScreenBinding
import com.example.batwaraapp.datamodels.UserModel
import java.util.ArrayList

class CreateLocalBillScreen : AppCompatActivity() {

    private lateinit var binding: ActivityCreateLocalBillScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_local_bill_screen)

    }

    /**
     * Code to handle incoming Intent to this activity.
     * Put the data into this launch params in the below LaunchSet.
     * */
    data class LaunchSet(
        val userId: Int? = null,
        val userList: List<UserModel>? = null
    )

    companion object : ActivityResultContract<LaunchSet?, Boolean>() {
        override fun createIntent(context: Context, params: LaunchSet?): Intent {
            val intent = Intent(context, CreateLocalBillScreen::class.java).apply {

            }
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return resultCode == RESULT_OK
        }
    }
}
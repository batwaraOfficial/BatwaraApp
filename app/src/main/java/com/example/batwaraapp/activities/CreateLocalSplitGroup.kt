package com.example.batwaraapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.databinding.DataBindingUtil
import com.example.batwaraapp.R
import com.example.batwaraapp.databinding.ActivityCreateLocalSplitGroupBinding
import com.example.batwaraapp.utils.InterfaceUtils.uniClick
import java.util.ArrayList

class CreateLocalSplitGroup : AppCompatActivity() {

    private lateinit var binding: ActivityCreateLocalSplitGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_local_split_group)
        supportActionBar?.hide()
        binding.toolbar.backButton.uniClick  { onBackPressed() }
        binding.addPersonButton.uniClick {  }
    }


    /**
     * Code to handle incoming Intent to this activity.
     * Put the data into this launch params in the below LaunchSet.
     * */
    data class LaunchSet(
        val userId: Int? = null
    )

    companion object : ActivityResultContract<LaunchSet?, Boolean>() {
        override fun createIntent(context: Context, params: LaunchSet?): Intent {
            val intent = Intent(context, CreateLocalSplitGroup::class.java).apply {}
            return intent
        }
        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return resultCode == RESULT_OK
        }
    }
}
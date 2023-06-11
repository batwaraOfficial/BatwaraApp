package com.example.batwaraapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.batwaraapp.R
import com.example.batwaraapp.adapters.LocalGroupAdapter
import com.example.batwaraapp.databinding.ActivityCreateLocalSplitGroupBinding
import com.example.batwaraapp.datamodels.UserModel
import com.example.batwaraapp.utils.InterfaceUtils.uniClick
import com.example.batwaraapp.viewmodels.LocalSplitGroupViewModel
import com.example.batwaraapp.viewmodels.ProfileViewModel
import java.util.ArrayList

class CreateLocalSplitGroup : AppCompatActivity() {

    private lateinit var binding: ActivityCreateLocalSplitGroupBinding
    private lateinit var adapter: LocalGroupAdapter
    private var createLocalBillScreen = registerForActivityResult(CreateLocalBillScreen) {}
    private val vm: LocalSplitGroupViewModel by lazy {
        ViewModelProvider(this).get(LocalSplitGroupViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Setting up binding.
         * Hiding action bar.
         * Setting up action of back button.
         * Setting lifeCycleOwner in order to set viewModel to binding.
         * Adding the viewModel to binding.
         * */
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_local_split_group)
        supportActionBar?.hide()
        binding.toolbar.backButton.uniClick  { onBackPressed() }
        binding.lifecycleOwner = this
        binding.vm = vm

        /**
         * Adapter related capabilities.
         * */
        adapter = LocalGroupAdapter()
        binding.recyclerView.adapter = adapter
        adapter.setOnRemoveClickListener {
            vm.removeMember(it)
        }
        vm.currentMemberList.observe(this) {
            adapter.setDataSet(it)
        }

        /**
         * Rest of the functionality.
         * */
        binding.addPersonButton.uniClick {
            var errorExists = false
            vm.currentMemberList.value?.let {
                if(vm.currentName.value.isNullOrEmpty()) {
                    errorExists = true
                    setErrorString("Please enter a name.")
                } else for(i in it) {
                    if(i.username == vm.currentName.value) {
                        setErrorString("A member with same name already exists!!")
                        errorExists = true
                        break
                    }
                }
            }
            if(!errorExists) {
                vm.addMember(UserModel(username = vm.currentName.value))
                binding.userNameTyper.text?.clear()
            }
        }

        binding.userNameTyper.doOnTextChanged { s, _, _, _ ->
            removeErrorString()
            vm.currentName.value = s.toString().trim() ?: ""
        }
        binding.createGroupButton.uniClick {
            if (vm.currentMemberList.value.isNullOrEmpty() || vm.currentMemberList.value?.size == 1) setErrorString("At least 2 members required to create a Group!!")
            else {

            }
        }
    }

    private fun setErrorString(error: String) { vm.errorString.value = error }
    private fun removeErrorString() { vm.errorString.value = null }

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
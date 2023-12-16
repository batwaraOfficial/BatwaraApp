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
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.batwaraapp.Constants.TAGS_LIST
import com.example.batwaraapp.R
import com.example.batwaraapp.adapters.GroupsTagsAdapter
import com.example.batwaraapp.adapters.LocalGroupAdapter
import com.example.batwaraapp.databinding.ActivityCreateLocalSplitGroupBinding
import com.example.batwaraapp.databinding.GroupDescriptionWindowBinding
import com.example.batwaraapp.datamodels.UserModel
import com.example.batwaraapp.datamodels.UserTag
import com.example.batwaraapp.utils.EmailForUse
import com.example.batwaraapp.utils.InterfaceUtils.uniClick
import com.example.batwaraapp.utils.SharedPrefsKeys
import com.example.batwaraapp.viewmodels.CreateGroupViewModel


class CreateGroupScreen : AppCompatActivity() {

    private lateinit var binding: ActivityCreateLocalSplitGroupBinding
    private lateinit var adapter: LocalGroupAdapter
    private lateinit var _adapter: GroupsTagsAdapter
    private val vm: CreateGroupViewModel by lazy {
        ViewModelProvider(this).get(CreateGroupViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * User Key common in all activities for doing DB operations
         * */
        val sharedPreferences: SharedPreferences? = this.getSharedPreferences(SharedPrefsKeys.MAIN_PREF_KEY, MODE_PRIVATE)
        val authPrefKey = sharedPreferences?.getString(SharedPrefsKeys.AUTH_PREF_KEY, "")
        val userKey = authPrefKey?.let { EmailForUse.getFirstPartEmail(it) }

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
                vm.addMember(UserModel(name = vm.currentName.value))
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
                /**
                 * Create Group Description Dialog.
                 * */
                val _binding = GroupDescriptionWindowBinding.inflate(layoutInflater)
                val dialog = AlertDialog.Builder(this).setView(_binding.root).create()
                dialog.setCanceledOnTouchOutside(true)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                _adapter = GroupsTagsAdapter()

                val gridLayoutManager = GridLayoutManager(applicationContext, 4, LinearLayoutManager.HORIZONTAL, false)
                _binding.recyclerView2.setLayoutManager(gridLayoutManager)
                _binding.recyclerView2.adapter = _adapter
                _adapter.setOnTagClickListener {
                    vm.selectTag(it)
                }

                _binding.saveGroup.uniClick {
                    Toast.makeText(this, "Please Wait.", Toast.LENGTH_SHORT).show()
                    if (userKey != null) {
                        vm.addGroupData(userKey, _binding.groupNameText.text.toString(), _binding.groupDescriptionText.text.toString(), {
                            Toast.makeText(this, "Group Saved.", Toast.LENGTH_SHORT).show()
                            onBackPressed()
                            dialog.dismiss()
                        }, {
                            Log.d("kkg-CreateLocalSplitGroup", "onCreate:${it?.message}")
                            Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
                        })
                    }
                }
                // init tags list
                var tempList: ArrayList<UserTag> = ArrayList()
                for(i in TAGS_LIST) tempList.add(UserTag(tag = i))

                vm.currentTags.observe(this) {
                    _adapter.setDataSet(it)
                }
                vm.currentTags.value = tempList
                dialog.show()
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
            val intent = Intent(context, CreateGroupScreen::class.java).apply {}
            return intent
        }
        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return resultCode == RESULT_OK
        }
    }
}
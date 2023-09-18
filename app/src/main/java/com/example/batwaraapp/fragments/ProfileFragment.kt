package com.example.batwaraapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.batwaraapp.R
import com.example.batwaraapp.activities.GroupsScreen
import com.example.batwaraapp.databinding.FragmentProfileBinding
import com.example.batwaraapp.utils.InterfaceUtils.uniClick
import com.example.batwaraapp.viewmodels.ProfileViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    val createGroupsScreen = registerForActivityResult(GroupsScreen) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentProfileBinding.inflate(inflater, container, false)

        binding.groupsButton.uniClick(true) {
            createGroupsScreen.launch(GroupsScreen.LaunchSet())
        }

        return binding.root
    }

}
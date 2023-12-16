package com.example.batwaraapp.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.batwaraapp.activities.CreateGroupScreen
import com.example.batwaraapp.activities.HomeScreen
import com.example.batwaraapp.databinding.FragmentLandingBinding
import com.example.batwaraapp.utils.InterfaceUtils.uniClick
import com.example.batwaraapp.utils.SharedPrefsKeys

class LandingFragment : Fragment() {

    private lateinit var binding: FragmentLandingBinding
    private val createLocalGroupScreen = registerForActivityResult(CreateGroupScreen) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentLandingBinding.inflate(inflater, container, false)

        binding.createGroupButton.uniClick(isAnimated = true) {
            createLocalGroupScreen.launch(CreateGroupScreen.LaunchSet())
        }

        binding.materialCardView.uniClick (true) {
            val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences(
                SharedPrefsKeys.MAIN_PREF_KEY,
                AppCompatActivity.MODE_PRIVATE
            )
            val editor = sharedPreferences?.edit()
            editor?.putString(SharedPrefsKeys.AUTH_PREF_KEY, "")
            editor?.apply()
            (requireActivity() as HomeScreen?)?.fragmentLogout()
        }

        return binding.root
    }
}
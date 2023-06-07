package com.example.batwaraapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.batwaraapp.R
import com.example.batwaraapp.databinding.FragmentHistoryBinding
import com.example.batwaraapp.databinding.FragmentLandingBinding

class LandingFragment : Fragment() {

    private lateinit var binding: FragmentLandingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentLandingBinding.inflate(inflater, container, false)

        return binding.root
    }
}
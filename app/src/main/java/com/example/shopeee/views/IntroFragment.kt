package com.example.shopeee.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shopeee.R
import com.example.shopeee.databinding.FragmentIntroductionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment : Fragment() {
    //private val appId = "shopeee-zuqyq"
    private lateinit var binding: FragmentIntroductionBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroductionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            signupRedirectBtn.setOnClickListener {
                findNavController().navigate(R.id.action_introFragment_to_registerFragment)
            }
            loginRedirectBtn.setOnClickListener {
                findNavController().navigate(R.id.action_introFragment_to_loginFragment)
            }
        }

    }

}
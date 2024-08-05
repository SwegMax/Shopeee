package com.example.shopeee.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shopeee.databinding.FragmentIntroductionBinding

class IntroFragment : Fragment() {
    /*lateinit var app : App
    private val appId = "shopeee-zuqyq"*/
    private lateinit var binding: FragmentIntroductionBinding
<<<<<<< HEAD:app/src/main/java/com/example/shopeee/views/FirstFragment.kt
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
    }
=======
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
        }
>>>>>>> 3fe3923b2cedaf376d9dbc543a8909dc303f6304:app/src/main/java/com/example/shopeee/views/IntroFragment.kt

}
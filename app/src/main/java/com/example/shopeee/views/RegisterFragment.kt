package com.example.shopeee.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.shopeee.databinding.FragmentRegisterBinding
import com.example.shopeee.repository.Resource
import com.example.shopeee.viewmodelMVVM.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply{
            signupBtn.setOnClickListener{
                val user = nameEdit.text.toString().trim()
                val password = passwordEdit.text.toString().trim()
                viewModel.register(user, password)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.observeRegisterState().collect { state ->
                when(state) {
                    is Resource.Loading -> {
                        //binding.signupBtn.startAnimation()
                    }
                    is Resource.Success -> {
                        // Registration succeeded
                        // You can navigate to another screen or show a success message here
                        Log.d(null,"Register onClickListener success")
                    }
                    is Resource.Error -> {
                        // Registration failed
                        // You can show an error message to the user here
                        Log.d(null,"Register onClickListener failed: ${state.message}")
                    }
                }
            }
        }

    }
}
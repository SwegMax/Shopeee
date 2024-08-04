package com.example.shopeee.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.shopeee.databinding.FragmentRegisterBinding
import com.example.shopeee.repository.RegisterValidation
import com.example.shopeee.repository.Resource
import com.example.shopeee.repository.User
import com.example.shopeee.viewmodelMVVM.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
                val user = User(
                    nameEdit.text.toString().trim(),
                    "lastNamePlaceHolder",
                    "emailPlaceHolder"
                )
                val password = passwordEdit.text.toString().trim()
                viewModel.register(user, password)
            }
        }

        lifecycleScope.launchWhenStarted {
                viewModel.register.collect {
                    when (it) {
                        is Resource.Loading -> {
                            // Update UI to show loading state
                            // For example: binding.signupBtn.startAnimation()
                        }
                        is Resource.Success -> {
                            // Update UI for success state
                            Log.d(null, "Register onClickListener success")
                        }
                        is Resource.Error -> {
                            // Update UI for error state
                            Log.d(null, "Register onClickListener failed")
                        }
                        else -> {
                            Log.d(null, "Register onClickListener failed")
                        }
                    }
                }
        }


        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // This block will be executed whenever the lifecycle is at least in the STARTED state
                viewModel.validation.collect { validation ->
                    if(validation.username is RegisterValidation.Failed) {
                        withContext(Dispatchers.Main) {
                            binding.nameEdit.apply {
                                requestFocus()
                                error = validation.username.message
                            }
                        }
                    }

                    if(validation.password is RegisterValidation.Failed) {
                        withContext(Dispatchers.Main) {
                            binding.passwordEdit.apply {
                                requestFocus()
                                error = validation.password.message
                            }
                        }
                    }
                }
            }
        }
    }
}
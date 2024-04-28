package com.example.shopeee.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.shopeee.R
import com.example.shopeee.databinding.FragmentLoginBinding
import com.example.shopeee.repository.Resource
import com.example.shopeee.viewmodelMVVM.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply{
            loginBtn.setOnClickListener{
                val user = userEdit.text.toString().trim()
                val password = passwordEdit.text.toString().trim()
                viewModel.login(user, password)
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // This block will be executed whenever the lifecycle is at least in the STARTED state
                viewModel.observeLoginState().collect { state ->
                    when (state) {
                        is Resource.Loading -> {
                            // Update UI to show loading state
                            // For example: binding.signupBtn.startAnimation()
                        }
                        is Resource.Success -> {
                            Intent(requireActivity(), MainActivity::class.java).also {intent ->
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            Log.d(null, "Login onClickListener success")
                        }
                        is Resource.Error -> {
                            // Update UI for error state
                            Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                            Log.d(null, "Login onClickListener failed")
                        }
                        else -> {
                            Log.d(null, "Login onClickListener failed")
                        }
                    }
                }
            }
        }

        /*lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // This block will be executed whenever the lifecycle is at least in the STARTED state
                viewModel.validation.collect { validation ->
                    if(validation.username is RegisterValidation.Failed) {
                        withContext(Dispatchers.Main) {
                            binding.userEdit.apply {
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
        }*/
    }
}
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
import androidx.navigation.fragment.findNavController
import com.example.shopeee.R
import com.example.shopeee.databinding.FragmentLoginBinding
import com.example.shopeee.repository.AnimationUtils
import com.example.shopeee.repository.Resource
import com.example.shopeee.viewmodelMVVM.LoginViewModel
import com.example.shopeee.views.dialog.setupBottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
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

        binding.tvDontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.apply {
            buttonLoginLogin.setOnClickListener {
                val email = edEmailLogin.text.toString().trim()
                val password = edPasswordLogin.text.toString()
                viewModel.login(email, password)
            }
        }

        binding.tvForgotPasswordLogin.setOnClickListener {
            setupBottomSheetDialog { email ->
                viewModel.resetPassword(email)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resetPassword.collect{
                when(it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        Snackbar.make(requireView(), " Reset link was sent to your email", Snackbar.LENGTH_LONG).show()
                    }
                    is Resource.Error -> {
                        Snackbar.make(requireView(), " Error: Cannot reset password", Snackbar.LENGTH_LONG).show()
                    }
                    else -> Unit
                }
            }
        }

        //can be replaced with launchWhenStarted
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // This block will be executed whenever the lifecycle is at least in the STARTED state
                viewModel.login.collect {
                    when (it) {
                        is Resource.Loading -> {
                            binding.buttonLoginLogin.startAnimation(AnimationUtils.loadingShake(context))
                        }
                        is Resource.Success -> {
                            binding.buttonLoginLogin.startAnimation(AnimationUtils.successShake(context))
                            //revertAnimation()?
                            Intent(requireActivity(), ShoppingActivity::class.java).also { intent ->
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                //won't let users logout by pressing back, exits app
                                startActivity(intent)
                            }
                            Log.d(null, "Login onClickListener success")
                        }

                        is Resource.Error -> {
                            binding.buttonLoginLogin.startAnimation(AnimationUtils.loadingShake(context))
                            Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_LONG).show()
                            Log.d(null, "Login onClickListener failed")
                        }

                        else -> {
                            Log.d(null, "Login onClickListener failed")
                        }
                    }
                }
            }
        }
    }
}
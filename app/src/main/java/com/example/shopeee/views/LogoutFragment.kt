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
import com.example.shopeee.databinding.FragmentLogoutBinding
import com.example.shopeee.repository.AnimationUtils
import com.example.shopeee.repository.Resource
import com.example.shopeee.viewmodelMVVM.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogoutFragment : Fragment(R.layout.fragment_logout) {
    private lateinit var binding: FragmentLogoutBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLogoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogout.setOnClickListener {
            viewModel.logout()
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.login.collect {
                    when (it) {
                        is Resource.Loading -> {
                            binding.buttonLogout.startAnimation(AnimationUtils.loadingShake(context))
                        }
                        is Resource.Success -> {
                            // Navigate to the login page after successful logout
                            Intent(requireActivity(), LoginRegisterActivity::class.java).also { intent ->
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                        }
                        is Resource.Error -> {
                            binding.buttonLogout.startAnimation(AnimationUtils.loadingShake(context))
                            Toast.makeText(requireContext(), "Logout Failed", Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Log.d(null, "Logout onClickListener failed")
                        }
                    }
                }
            }
        }

    }

}

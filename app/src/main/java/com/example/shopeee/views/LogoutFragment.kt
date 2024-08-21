package com.example.shopeee.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shopeee.R
import com.example.shopeee.databinding.FragmentLogoutBinding
import com.example.shopeee.viewmodelMVVM.LogoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment : Fragment(R.layout.fragment_logout) {
    private lateinit var binding: FragmentLogoutBinding
    private val viewModel by viewModels<LogoutViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLogoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(requireActivity(), LoginRegisterActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}

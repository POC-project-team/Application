package ru.nsu.poc2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.nsu.poc2.databinding.FragmentLoginBinding
import ru.nsu.poc2.network.StatusValue
import ru.nsu.poc2.utils.LogTags
import ru.nsu.poc2.viewmodels.LoginViewModel

class LoginFragment: Fragment(){
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        loginObserver()
        binding.login.setOnClickListener {
            login()
        }
        return binding.root
    }

    private fun login() {
        viewModel.login(binding.email.text.toString(), binding.password.text.toString())
    }

    private fun loginObserver() {
        viewModel.status.observe(viewLifecycleOwner){
            when(it){
                StatusValue.ERROR -> {

                }
                StatusValue.SUCCESS->{

                }
                StatusValue.LOADING->{

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


}
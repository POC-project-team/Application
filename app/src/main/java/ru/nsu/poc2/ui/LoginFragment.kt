package ru.nsu.poc2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.nsu.poc2.R
import ru.nsu.poc2.databinding.FragmentLoginBinding
import ru.nsu.poc2.network.StatusValue
import ru.nsu.poc2.utils.FieldValidators
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
        binding.errorPassword.visibility = View.INVISIBLE
        binding.errorEmail.visibility = View.INVISIBLE
        if (!FieldValidators.validateFieldsNotEmpty(binding.email.text.toString())){
            if (!FieldValidators.validateFieldsNotEmpty(binding.password.text.toString())){
                binding.errorEmail.text = getString(R.string.enter_email)
                binding.errorPassword.text = getString(R.string.enter_password)
                binding.errorPassword.visibility = View.VISIBLE
                binding.errorEmail.visibility = View.VISIBLE
                return
            }
            binding.errorEmail.text = getString(R.string.enter_email)
            binding.errorEmail.visibility = View.VISIBLE
            return
        }
        if (!FieldValidators.validateFieldsNotEmpty(binding.password.text.toString())){
            binding.errorPassword.text = getString(R.string.enter_password)
            binding.errorPassword.visibility = View.VISIBLE
            return
        }
        if (!FieldValidators.validateEmail(binding.email.text.toString())){
            binding.errorEmail.text = getString(R.string.not_email_error)
            binding.errorEmail.visibility = View.VISIBLE
            return
        }
        viewModel.login(binding.email.text.toString(), binding.password.text.toString())
    }

    private fun loginObserver() {
        viewModel.status.observe(viewLifecycleOwner){
            when(it){
                StatusValue.ERROR -> {
                    binding.loadingBar.visibility = View.INVISIBLE
                    Toast.makeText(context, "Error while login", Toast.LENGTH_SHORT).show()
                }
                StatusValue.SUCCESS->{
                    binding.loadingBar.visibility = View.INVISIBLE

                }
                StatusValue.LOADING->{
                    binding.loadingBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


}
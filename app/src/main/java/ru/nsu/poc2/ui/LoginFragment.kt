package ru.nsu.poc2.ui

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ru.nsu.poc2.PocApplication
import ru.nsu.poc2.R
import ru.nsu.poc2.databinding.FragmentLoginBinding
import ru.nsu.poc2.network.StatusValue
import ru.nsu.poc2.utils.FieldValidators
import ru.nsu.poc2.utils.LogTags
import ru.nsu.poc2.viewmodels.LoginViewModel
import ru.nsu.poc2.viewmodels.LoginViewModelFactory

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
class LoginFragment: Fragment(){
    private var binding: FragmentLoginBinding? = null
    private val loginViewModel: LoginViewModel by activityViewModels {
        LoginViewModelFactory((activity?.application as PocApplication).database.loginDao())
    }
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        loginObserver()
        binding!!.login.setOnClickListener {
            login()
        }
        binding!!.register.setOnClickListener {
            startRegisterFragment()
        }
        binding!!.restorePassword.setOnClickListener {
            startRestorePasswordFragment()
        }
        return binding!!.root
    }

    private fun startRestorePasswordFragment() {
        Toast.makeText(context, "На данный момент не поддерживается сервером", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = findNavController(requireView())
    }

    private fun startRegisterFragment() {
        Log.d(LogTags.LOGIN, "Starting registration fragment")
//        navController.navigate(R.id.registrationFragment)
        findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    private fun login() {
        binding!!.errorPassword.visibility = View.INVISIBLE
        binding!!.errorEmail.visibility = View.INVISIBLE
        if (!FieldValidators.validateFieldsNotEmpty(binding!!.email.text.toString())){
            if (!FieldValidators.validateFieldsNotEmpty(binding!!.password.text.toString())){
                binding!!.errorEmail.text = getString(R.string.enter_email)
                binding!!.errorPassword.text = getString(R.string.enter_password)
                binding!!.errorPassword.visibility = View.VISIBLE
                binding!!.errorEmail.visibility = View.VISIBLE
                return
            }
            binding!!.errorEmail.text = getString(R.string.enter_email)
            binding!!.errorEmail.visibility = View.VISIBLE
            return
        }
        if (!FieldValidators.validateFieldsNotEmpty(binding!!.password.text.toString())){
            binding!!.errorPassword.text = getString(R.string.enter_password)
            binding!!.errorPassword.visibility = View.VISIBLE
            return
        }
        if (!FieldValidators.validateEmail(binding!!.email.text.toString())){
            binding!!.errorEmail.text = getString(R.string.not_email_error)
            binding!!.errorEmail.visibility = View.VISIBLE
            return
        }
        loginViewModel.login(binding!!.email.text.toString(), binding!!.password.text.toString())

    }

    private fun loginObserver() {
        loginViewModel.status.observe(viewLifecycleOwner){
            when(it){
                StatusValue.ERROR -> {
                    binding!!.loadingBar.visibility = View.INVISIBLE
                    Toast.makeText(context, getString(R.string.error_while_login), Toast.LENGTH_SHORT).show()
                }
                StatusValue.SUCCESS->{
                    binding!!.loadingBar.visibility = View.INVISIBLE
                    val intent = Intent(context, HomeActivity::class.java)
                    startActivity(intent)
                }
                StatusValue.LOADING->{
                    binding!!.loadingBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}
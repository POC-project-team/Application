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
import ru.nsu.poc2.databinding.FragmentRegistrationBinding
import ru.nsu.poc2.network.StatusValue
import ru.nsu.poc2.repositories.RegistrationRepository
import ru.nsu.poc2.utils.FieldValidators
import ru.nsu.poc2.utils.LogTags
import ru.nsu.poc2.viewmodels.RegistrationViewModel
import java.lang.reflect.Field

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegistrationViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        registrationObserver()
        binding.register.setOnClickListener {
            register()
        }
        return binding.root
    }

    private fun register() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val confirmPassword = binding.passwordConfirm.text.toString()
        val errorEmail = binding.errorEmail
        val errorPassword = binding.errorPassword
        val errorConfirmPassword = binding.errorConfirmPassword
        errorEmail.visibility = View.INVISIBLE
        errorPassword.visibility = View.INVISIBLE
        errorConfirmPassword.visibility = View.INVISIBLE
        if (!FieldValidators.validateFieldsNotEmpty(email)
            && !FieldValidators.validateFieldsNotEmpty(password)
            && !FieldValidators.validateFieldsNotEmpty(confirmPassword)
        ) {
            errorEmail.text = getString(R.string.enter_email)
            errorPassword.text = getString(R.string.enter_password)
            errorConfirmPassword.text = getString(R.string.enter_password)
            errorEmail.visibility = View.VISIBLE
            errorPassword.visibility = View.VISIBLE
            errorConfirmPassword.visibility = View.VISIBLE
            return
        }
        if (!FieldValidators.validateFieldsNotEmpty(email)
            && !FieldValidators.validateFieldsNotEmpty(password)
        ) {
            errorEmail.text = getString(R.string.enter_email)
            errorPassword.text = getString(R.string.enter_password)
            errorEmail.visibility = View.VISIBLE
            errorPassword.visibility = View.VISIBLE
            return
        }
        if (!FieldValidators.validateFieldsNotEmpty(email)) {
            errorEmail.text = getString(R.string.enter_email)
            errorEmail.visibility = View.VISIBLE
            return
        }

        if (!FieldValidators.validateFieldsNotEmpty(password)) {
            errorPassword.text = getString(R.string.enter_password)
            errorPassword.visibility = View.VISIBLE
            return
        }
        if (!FieldValidators.validateFieldsNotEmpty(confirmPassword)) {
            errorConfirmPassword.text = getString(R.string.enter_password)
            errorConfirmPassword.visibility = View.VISIBLE
            return
        }
        if(!FieldValidators.validateEmail(email)){
            errorEmail.text = getString(R.string.not_email_error)
            errorEmail.visibility = View.VISIBLE
            return
        }
        val validatePassword = FieldValidators.validatePassword(password)
        if (validatePassword != 0) {
            errorPassword.text = getString(validatePassword)
            errorPassword.visibility = View.VISIBLE
            return
        }
        if (password != confirmPassword) {
            errorPassword.text = getString(R.string.not_equal_password)
            errorConfirmPassword.text = getString(R.string.not_equal_password)
            return
        }
        viewModel.registerUser(email, password)
    }


    private fun registrationObserver() {
        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                StatusValue.SUCCESS -> {
                    Log.d(LogTags.REGISTRATION, "Successfully created user")
                    binding.loadingBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        context,
                        getString(R.string.successful_registration),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                StatusValue.ERROR -> {
                    Log.e(LogTags.REGISTRATION, "Error while registration")
                    binding.loadingBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        context,
                        getString(R.string.error_while_registration),
                        Toast.LENGTH_SHORT
                    ).show()

                }
                StatusValue.LOADING -> {
                    Log.d(LogTags.REGISTRATION, "Loading")
                    binding.loadingBar.visibility = View.VISIBLE
                }
            }
        }
    }


}
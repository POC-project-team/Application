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
import ru.nsu.poc2.utils.LogTags
import ru.nsu.poc2.viewmodels.RegistrationViewModel

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
class RegistrationFragment: Fragment(){
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
        TODO("Not yet implemented")
    }

    private fun registrationObserver() {
        viewModel.status.observe(viewLifecycleOwner){
            when(it){
                StatusValue.SUCCESS->{
                    Log.d(LogTags.REGISTRATION, "Successfully created user")
                    binding.loadingBar.visibility = View.INVISIBLE
                    Toast.makeText(context, getString(R.string.successful_registration), Toast.LENGTH_SHORT).show()
                }
                StatusValue.ERROR->{
                    Log.e(LogTags.REGISTRATION, "Error while registration")
                    binding.loadingBar.visibility = View.INVISIBLE
                    Toast.makeText(context, getString(R.string.error_while_registration), Toast.LENGTH_SHORT).show()
                }
                StatusValue.LOADING->{
                    Log.d(LogTags.REGISTRATION, "Loading")
                    binding.loadingBar.visibility = View.VISIBLE
                }
            }
        }
    }
}
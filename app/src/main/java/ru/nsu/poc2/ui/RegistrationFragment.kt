package ru.nsu.poc2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.nsu.poc2.databinding.FragmentRegistrationBinding

class RegistrationFragment: Fragment(){
    private lateinit var binding: FragmentRegistrationBinding

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
        TODO("Not yet implemented")
    }
}
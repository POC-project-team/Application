package ru.nsu.poc2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import ru.nsu.poc2.PocApplication
import ru.nsu.poc2.R
import ru.nsu.poc2.databinding.ActivityMainBinding
import ru.nsu.poc2.network.StatusValue
import ru.nsu.poc2.viewmodels.LoginViewModel
import ru.nsu.poc2.viewmodels.LoginViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((this.application as PocApplication).database.loginDao())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        loginViewModel.autoLogin()
        observe()
    }

    private fun observe() {
        loginViewModel.status.observe(this) {
            when (it) {
                StatusValue.SUCCESS -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
                StatusValue.ERROR -> {
                    startLoginFragment()
                    findNavController(binding.navHostFragment.getFragment()).navigate(R.id.loginFragment)
                }
                StatusValue.LOADING -> {

                }
            }
        }
    }

    private fun startLoginFragment() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}
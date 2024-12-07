package com.elidacaceres.tpfinal
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elidacaceres.tpfinal.databinding.ActivityLoginBinding
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            loginViewModel.login(email, password)
        }

        binding.registerTextView.setOnClickListener {
            Toast.makeText(this, "Navigate to Register Screen", Toast.LENGTH_SHORT).show()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        loginViewModel.loginResult.observe(this) { result ->
            if (result) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                // Navigate to next screen
            } else {
                Toast.makeText(this, "Login Failed. Check credentials.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    fun login(email: String, password: String) {
        // Simple validation for demo purposes
        _loginResult.value = email == "user@example.com" && password == "password123"
    }
}
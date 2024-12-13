package com.elidacaceres.tpfinal

import LoginViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.elidacaceres.tpfinal.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observa los cambios en el ViewModel
        observeViewModel()

        // Configura el botón de inicio de sesión
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {
                loginViewModel.login(email, password) // Llama al método del ViewModel
            } else {
                Toast.makeText(this, "Por favor ingresa tu email y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        // Configura el botón para ir a la pantalla de registro (si tienes una)
        binding.registerTextView.setOnClickListener {
            Toast.makeText(this, "Navegar a la pantalla de registro", Toast.LENGTH_SHORT).show()
            // Navegar a la pantalla de registro si corresponde
        }
    }

    private fun observeViewModel() {
        // Observa el resultado del inicio de sesión
        loginViewModel.loginResult.observe(this) { result ->
            if (result) {
                Toast.makeText(this, "Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, DashboardActivity::class.java) // Cambia por tu pantalla principal
                startActivity(intent)
                finish()
            } else {
                val error = loginViewModel.errorMessage.value
                if (!error.isNullOrEmpty()) {
                    Toast.makeText(this, "Error de inicio de sesión: $error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Observa los mensajes de error generales
        loginViewModel.errorMessage.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

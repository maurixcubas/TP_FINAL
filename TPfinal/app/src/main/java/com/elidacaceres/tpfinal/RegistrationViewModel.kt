import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.elidacaceres.tpfinal.RegisterRequest
import com.elidacaceres.tpfinal.RegisterResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elidacaceres.tpfinal.RetrofitInstance

class RegisterViewModel : ViewModel() {

    // Estados observables
    private val _registrationState = MutableLiveData<RegistrationState>()
    val registrationState: LiveData<RegistrationState> get() = _registrationState

    // Función para manejar el registro
    fun registerUser(firstName: String, lastName: String, email: String, phoneNumber: String, password: String) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            _registrationState.value = RegistrationState.Error("Todos los campos son obligatorios")
            return
        }

        // Lanzar una coroutine para realizar la llamada a Retrofit
        viewModelScope.launch {
            _registrationState.value = RegistrationState.Loading
            try {
                val request = RegisterRequest(firstName, lastName, email, phoneNumber, password)
                val response = RetrofitInstance.api.register(request)

                if (response.isSuccessful && response.body()?.success == true) {
                    _registrationState.value = RegistrationState.Success("Registro exitoso")
                } else {
                    val errorMessage = response.body()?.message ?: "Error en el registro"
                    _registrationState.value = RegistrationState.Error(errorMessage)
                }
            } catch (e: Exception) {
                _registrationState.value = RegistrationState.Error("Error de conexión: ${e.message}")
            }
        }
    }
}

// Estados para manejar el resultado
sealed class RegistrationState {
    object Idle : RegistrationState()
    object Loading : RegistrationState()
    data class Success(val message: String) : RegistrationState()
    data class Error(val errorMessage: String) : RegistrationState()
}
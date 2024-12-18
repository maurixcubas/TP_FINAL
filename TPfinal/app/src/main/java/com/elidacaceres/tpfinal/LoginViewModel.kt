import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elidacaceres.tpfinal.LoginRequest
import com.elidacaceres.tpfinal.RetrofitInstance
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        // Ejecuta la llamada dentro de una corutina
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.login(loginRequest)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.success == true) {
                        _loginResult.value = true
                    } else {
                        _loginResult.value = false
                        _errorMessage.value = body?.message ?: "Credenciales incorrectas"
                    }
                } else {
                    _loginResult.value = false
                    _errorMessage.value = "Error del servidor: ${response.code()}"
                }
            } catch (e: Exception) {
                // Captura errores de red o excepciones
                _loginResult.value = false
                _errorMessage.value = "Error de red: ${e.message}"
            }
        }
    }
}
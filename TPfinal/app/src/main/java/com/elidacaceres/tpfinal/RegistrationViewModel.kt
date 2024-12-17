import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elidacaceres.tpfinal.LoginRequest
import com.elidacaceres.tpfinal.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    // Estados observables
    private val _registrationResult = MutableLiveData<String>()
    val registrationResult: LiveData<String> get() = _registrationResult

    // Función para manejar el registro
    fun registerUser(firstName: String, lastName:String, email: String, phoneNumber: String, password: String) {
        when {
            firstName.isEmpty() ||lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()-> {
                _registrationResult.value = "Todos los campos son obligatorios"
            }

            else -> {
                // Simula el proceso de registro (Aquí podrías guardar en una DB o API)
                _registrationResult.value = "Usuario registrado con éxito"
            }
        }
    }


}
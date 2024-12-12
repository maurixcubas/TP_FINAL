import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elidacaceres.tpfinal.LoginRequest
import com.elidacaceres.tpfinal.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        RetrofitInstance.api.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
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
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.value = false
                _errorMessage.value = "Error de red: ${t.message}"
            }
        })
    }
}

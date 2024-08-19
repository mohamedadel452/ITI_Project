package com.example.iti_project.ui.actitvity1.mainActitvity.loginFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.models.UserModel
import com.example.iti_project.data.repo.UserRepo.UserRepoImp
import kotlinx.coroutines.launch

class LoginViewModel ( private val UserRepo: UserRepoImp
                       //private val sharedPreferences: SharedPreferenceImp
) : ViewModel() {

    private val _LoginState = MutableLiveData<UiState<UserModel>>()
    val LoginState: LiveData<UiState<UserModel>> get() = _LoginState

    fun Userlogin(email: String, password: String) {
        _LoginState.value = UiState.Loading

        viewModelScope.launch {
            val user = UserRepo.getUserByEmail(email)
            if (user != null) {
                if (user.password == password) {
                    _LoginState.value = UiState.Success(user)
                    UserRepo.setLoggedIn(true)
                } else {

                    _LoginState.value = UiState.Error("Invalid password")
                }
            } else {
                _LoginState.value = UiState.Error("User not found")
            }
        }
    }
}

class LoginViewModelFactory(
    private val userRepo: UserRepoImp,
    //private val sharedPreferences: SharedPreferenceImp
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return LoginViewModel(userRepo) as T
    }

}
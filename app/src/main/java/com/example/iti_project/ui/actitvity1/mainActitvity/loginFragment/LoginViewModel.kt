package com.example.iti_project.ui.actitvity1.mainActitvity.loginFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.models.UserModel
import com.example.iti_project.data.repo.UserRepo.UserRepo
import com.example.iti_project.data.repo.UserRepo.UserRepoImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepo: UserRepo
) : ViewModel() {

    private val _LoginState = MutableLiveData<UiState<UserModel>>()
    val LoginState: LiveData<UiState<UserModel>> get() = _LoginState

    fun Userlogin(email: String, password: String) {
        _LoginState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO)  {
            val user = async { userRepo.getUserByEmail(email)}
            val u = user.await()
            if (u != null) {
                if (u.password == password) {
                    _LoginState.postValue( UiState.Success(u))
                    userRepo.setLoggedIn(email)
                    Log.i("login", " "+ userRepo.getLoggedIn())
                } else {

                    _LoginState.postValue( UiState.Error("Invalid password"))
                }
            } else {
                _LoginState.postValue( UiState.Error("User not found"))
            }
        }
    }


    fun addFavorite(id:String){
        viewModelScope.launch(Dispatchers.IO) {
//            async {userRepo.getUserByEmail("xss")}
            async { userRepo.addFavouriteRecipe(id)}
        }
    }
}

class LoginViewModelFactory(
    private val userRepo: UserRepoImp
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
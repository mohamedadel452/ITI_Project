package com.example.iti_project.ui.actitvity1.mainActitvity.splashFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceInterface
import com.example.iti_project.data.repo.UserRepo.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(private val userRepo: UserRepo) : ViewModel()
{

    fun checkLoginStatus(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isLoggedIn = withContext(Dispatchers.IO) {
                userRepo.getLoggedIn() != "" && userRepo.getLoggedIn() !="Not Found"
            }
            if (isLoggedIn != null)
                onResult(isLoggedIn)
        }
    }

}
class SplashViewModelFactory(private val userRepo: UserRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(userRepo) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
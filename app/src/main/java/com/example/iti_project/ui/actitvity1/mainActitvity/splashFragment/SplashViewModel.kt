package com.example.iti_project.ui.actitvity1.mainActitvity.splashFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(private val sharedPreferences: SharedPreferenceInterface) : ViewModel()
{

    fun checkLoginStatus(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isLoggedIn = withContext(Dispatchers.IO) {
                sharedPreferences.isLoggedIn()
            }
            onResult(isLoggedIn)
        }
    }

}
class SplashViewModelFactory(private val sharedPreferences: SharedPreferenceInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(sharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.iti_project.ui.actitvity1.mainActitvity.registerFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RegisterViewModel : ViewModel(

){
     fun checkedPassword(password : String): Boolean{
        if (password.length < 8) return false
        if (password.firstOrNull { it.isDigit() } == null) return false
        if (password.filter { it.isLetter() }.firstOrNull { it.isUpperCase() } == null) return false
        if (password.filter { it.isLetter() }.firstOrNull { it.isLowerCase() } == null) return false
        if (password.firstOrNull { !it.isLetterOrDigit() } == null) return false
        return true
    }

    fun checkedEmail(email : String): Boolean{
        if(email.contains("@") && email.contains(".")) {
           return true
        }
        return false
    }

    fun insertData(userName : String , email: String , password: String): Boolean{
        Log.i("TAG", userName +" " + email + " "+ password  )
        return true
    }
}

class RegisterViewModelFactory() :
    ViewModelProvider.Factory {
//    private val userRepository
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return RegisterViewModel(userRepository) as T
//    }
}
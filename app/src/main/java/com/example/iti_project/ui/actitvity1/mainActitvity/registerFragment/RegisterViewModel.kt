package com.example.iti_project.ui.actitvity1.mainActitvity.registerFragment

import android.service.autofill.UserData
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.models.UserModel
import com.example.iti_project.data.repo.UserRepo.UserRepo
import com.example.iti_project.data.repo.UserRepo.UserRepoImp
import com.example.iti_project.ui.actitvity1.mainActitvity.splashFragment.SplashViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepo
) : ViewModel(){

    private val _isSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccess: LiveData<Boolean> get() = _isSuccess
     fun checkedPassword(password : String): Boolean{
        if (password.length < 8) return false
        if (password.firstOrNull { it.isDigit() } == null) return false
        if (password.filter { it.isLetter() }.firstOrNull { it.isUpperCase() } == null) return false
        if (password.filter { it.isLetter() }.firstOrNull { it.isLowerCase() } == null) return false
        if (password.firstOrNull { !it.isLetterOrDigit() } == null) return false
        return true
    }

    fun checkedEmail(email : String): Boolean{
        return email.contains("@") && email.contains(".")
    }

    fun insertData(userName : String, email: String, password: String){
//        Log.i("TAG", userName +" " + email + " "+ password  )
        val user  = UserModel(email =  email , userName = userName , password = password)
        var isInserted : Long

        viewModelScope.launch(Dispatchers.IO) {
           val response =  async {userRepository.insertUser(user)}

            isInserted =  response.await()
            _isSuccess.postValue( isInserted != -1L)
        }

    }
}

class RegisterViewModelFactory(private val userRepository : UserRepo) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(userRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }


    }
}
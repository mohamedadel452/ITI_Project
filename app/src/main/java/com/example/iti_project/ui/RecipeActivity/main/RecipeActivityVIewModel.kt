package com.example.iti_project.ui.RecipeActivity.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.repo.UserRepo.UserRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class RecipeActivityVIewModel (private val repository: UserRepo): ViewModel() {

    private val _LoginState = MutableLiveData<Boolean>()
    val LoginState: LiveData<Boolean> get() = _LoginState

    fun setLoggedIn(): Boolean {

//        CoroutineScope(viewModelScope.coroutineContext).launch(Dispatchers.IO) {

        return  repository.setLoggedIn("Not Found")
//        GlobalScope.launch(Dis/patchers.IO){ delay(200)}
//            Log.i("response view","  "+ result.await() )

//        }
    }






}

class RecipeActivityVIewModelFactory (private val repository: UserRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeActivityVIewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeActivityVIewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
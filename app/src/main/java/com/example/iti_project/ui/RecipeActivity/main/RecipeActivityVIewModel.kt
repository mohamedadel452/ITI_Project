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

    fun setLoggedIn(): Boolean {
        return  repository.setLoggedIn("Not Found")
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
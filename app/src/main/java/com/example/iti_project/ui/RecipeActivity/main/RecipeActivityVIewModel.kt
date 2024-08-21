package com.example.iti_project.ui.RecipeActivity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.repo.UserRepo.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RecipeActivityVIewModel (private val repository: UserRepo): ViewModel() {

    private val _LoginState = MutableLiveData<Boolean>()
    val LoginState: LiveData<Boolean> get() = _LoginState

    fun setLoggedIn() {


        viewModelScope.launch(Dispatchers.IO) {

            val result = async {  repository.setLoggedIn("Not Found")}
            _LoginState.postValue(result.await())
        }
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
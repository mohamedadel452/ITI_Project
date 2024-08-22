package com.example.iti_project.ui.RecipeActivity.HomeFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.MealsResponse
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.Meals.MealsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragmentViewModel(private val repository: MealsRepo) : ViewModel() {

    private val _meals: MutableLiveData<UiState<List<Meals>>> = MutableLiveData(UiState.Loading)
    val meals: LiveData<UiState<List<Meals>>> = _meals


    private val _isSaved = MutableLiveData<Boolean>(false)
    val isSaved: LiveData<Boolean> = _isSaved


    // Call this method to update the state
    fun setIsSaved(saved: Boolean) {
        _isSaved.value = saved
    }

    fun getMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = async {  repository.getMeals()}.await()

                // Switch to Main thread for UI updates
                    when (response) {
                        is ResultState.Success -> {
                            _meals.postValue(UiState.Success(response.data.meals))
                        }
                        is ResultState.Error -> {
                            _meals.postValue(UiState.Error(response.errorMessage))
                        }
                    }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("HomeFragmentViewModel", "Error fetching meals", e)
                    _meals.postValue(UiState.Error(e.toString()))
                }
            }
        }
    }

}

class ProductViewModelFactory(private val repository: MealsRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeFragmentViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

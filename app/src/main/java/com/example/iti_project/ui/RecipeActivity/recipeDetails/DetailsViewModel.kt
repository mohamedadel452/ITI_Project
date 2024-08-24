package com.example.iti_project.ui.RecipeActivity.DetailsFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.models.MealsDetails
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.Meals.MealsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(private val repository: MealsRepo) : ViewModel() {

    private val _mealDetails = MutableLiveData<UiState<MealsDetails>>(UiState.Loading)
    val mealDetails: LiveData<UiState<MealsDetails>> = _mealDetails



    fun getDetails(id: String) {

        viewModelScope.launch(Dispatchers.IO) {

            if (id.isNotBlank() && _mealDetails.value is UiState.Loading) {


                val result = async {
                    repository.getMealsDetails(id)
                }.await()

                withContext(Dispatchers.Main){
                    when (result) {
                        is ResultState.Error -> {
                            _mealDetails.postValue(UiState.Error(result.errorMessage))
                        }

                        is ResultState.Success -> {
                            val meals = result.data?.meals
                            if (!meals.isNullOrEmpty()) {
                                Log.d("mealDetails", meals.first().toString())
                                _mealDetails.postValue(UiState.Success(meals.first()))
                            } else {
                                _mealDetails.postValue(UiState.Error("No meal details found"))
                                Log.d("mealDetails", "No meals found for id: $id or data is null")
                            }
                        }



                    }
                }
            }
        }
    }
}

class DetailsViewModelFactory(private val repository: MealsRepo): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if(modelClass.isAssignableFrom(DetailsViewModel::class.java)){
            return DetailsViewModel(repository) as T}
        else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
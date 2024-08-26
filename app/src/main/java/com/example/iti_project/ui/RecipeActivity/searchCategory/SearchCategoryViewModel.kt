package com.example.iti_project.ui.RecipeActivity.searchCategory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.models.CategoryMealResponse
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.CategoryRepo.CategoryRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchCategoryViewModel (private val categoryRepo: CategoryRepo) : ViewModel(){

    private val _meals: MutableLiveData<UiState<List<CategoryMealResponse>>> = MutableLiveData(UiState.Loading)
    val meals: LiveData<UiState<List<CategoryMealResponse>>> = _meals

    fun getMealsByCategoryName(categoryName: String) {

        Log.e( "getMealsByCategoryName:","view model and the category name is $categoryName")
        if (_meals.value !is  UiState.Success ) {
            viewModelScope.launch(Dispatchers.IO) {
                try {

                    val response = async { categoryRepo.getCategoryByName(categoryName) }.await()

                    // Switch to Main thread for UI updates
                    when (response) {
                        is ResultState.Success -> {
                            Log.e( "getMealsByCategoryName:","hi am here ")
                            _meals.postValue(UiState.Success(response.data.meals))
                            Log.e( "getMealsByCategoryName:",response.data.meals.toString())
                        }

                        is ResultState.Error -> {

                            _meals.postValue(UiState.Error(response.errorMessage))
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.e("HomeFragmentViewModel", "Error fetching categories", e)
                        _meals.postValue(UiState.Error(e.toString()))
                    }
                }
            }
        }
    }



}
class SearchCategoryViewModelFactory(
    private val categoryRepo: CategoryRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchCategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchCategoryViewModel(categoryRepo) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
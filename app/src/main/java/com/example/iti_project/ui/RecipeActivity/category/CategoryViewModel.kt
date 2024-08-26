package com.example.iti_project.ui.RecipeActivity.category

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.models.Category
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.CategoryRepo.CategoryRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel(private val categoryRepo: CategoryRepo)
    : ViewModel()
{

    private val _categories: MutableLiveData<UiState<List<Category>>> = MutableLiveData(UiState.Loading)
    val categories: LiveData<UiState<List<Category>>> = _categories

    fun getCategories() {

        Log.e( "getCategoriesCode:","view model")
        if (_categories.value !is  UiState.Success ) {
            viewModelScope.launch(Dispatchers.IO) {
                try {

                    val response = async { categoryRepo.getCategories() }.await()

                    // Switch to Main thread for UI updates
                    when (response) {
                        is ResultState.Success -> {
                            _categories.postValue(UiState.Success(response.data.categories))
                        }

                        is ResultState.Error -> {
                            _categories.postValue(UiState.Error(response.errorMessage))
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.e("HomeFragmentViewModel", "Error fetching categories", e)
                        _categories.postValue(UiState.Error(e.toString()))
                    }
                }
            }
        }
    }


}
class CategoryViewModelFactory(
    private val categoryRepo: CategoryRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(categoryRepo) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
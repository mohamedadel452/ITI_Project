package com.example.iti_project.ui.actitvity1.mainActitvity.Search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.Meals.MealsRepo
import com.example.iti_project.data.repo.Meals.MealsRepoImpl
import com.example.iti_project.data.repo.UserRepo.UserRepo
import com.example.iti_project.data.repo.UserRepo.UserRepoImp
import com.example.iti_project.ui.actitvity1.mainActitvity.loginFragment.LoginViewModel
import kotlinx.coroutines.launch

class SearchViewModel (private val Mealsrepo:  MealsRepo
) : ViewModel() {
    private val _search: MutableLiveData<UiState<List<Meals>>> = MutableLiveData()
    val search: LiveData<UiState<List<Meals>>> get() = _search

    fun searchMeals(query: String) {
        viewModelScope.launch {
            val result = Mealsrepo.getMealbyname(query)
            when (result) {
                is ResultState.Success -> {
                    val meals =
                        result.data.meals ?: emptyList()
                    _search.postValue(UiState.Success(meals))
                }

                is ResultState.Error -> {
                    _search.postValue(UiState.Error(result.errorMessage)) // Posting an error state with the error message
                }

                else -> {
                    _search.postValue(UiState.Error("Unknown error"))
                }
            }
        }
    }


    class SearchViewModelFactory(
        private val Mealsrepo: MealsRepoImpl,

        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return SearchViewModel(Mealsrepo) as T
        }

    }
}




package com.example.iti_project.ui.RecipeActivity.SearchFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.Meals.MealsRepo
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepoImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel (private val mealsRepo: MealsRepo,
                       private val favoriteRepo: FavoriteRecipeRepoImp
) : ViewModel() {

    private val _search = MutableLiveData<UiState<List<Meals>>>()
    val search: LiveData<UiState<List<Meals>>> get() = _search
    var favoriteUserIds = MediatorLiveData<List<String>>()

    init {

        viewModelScope.launch {
            favoriteUserIds.addSource(favoriteRepo.favoriteRecipeIDs) {
                Log.i("ya rab", ": " + it.size)
                favoriteUserIds.postValue(it)
            }
        }

    }

    fun searchMeals(query: String) {
        viewModelScope.launch {
            val result = mealsRepo.getMealbyname(query)
            when (result) {
                is ResultState.Success -> {
                    val meals = result.data.meals ?: emptyList()
                    _search.postValue(UiState.Success(meals))
                }
                is ResultState.Error -> {
                    _search.postValue(UiState.Error(result.errorMessage))
                }
                else -> {
                    _search.postValue(UiState.Error("Unknown error"))
                }
            }
        }
    }

    fun addMealToFavorites(meal: Meals) {
        if (meal != null) {

            viewModelScope.launch(Dispatchers.IO) {

                Log.i("addeddddd", "yes 2")
                meal.count += 1
                favoriteRepo.addFavouriteRecipe(meal)
            }

        }
    }

    fun deleteFavoriteRecipe(meal: Meals){
        viewModelScope.launch(Dispatchers.IO) {
            meal.count -= 1
            favoriteRepo.deleteFavouriteRecipeList(meal)
        }
    }

    // Factory for creating an instance of SearchViewModel
    class SearchViewModelFactory(
        private val mealsRepo: MealsRepo,
        private val favoriteRepo: FavoriteRecipeRepoImp
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(mealsRepo, favoriteRepo) as T
        }
    }
}
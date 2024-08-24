package com.example.iti_project.ui.RecipeActivity.homeFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.Meals.MealsRepo
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragmentViewModel(
    private val repository: MealsRepo,
    private val favoriteRecipeRepo: FavoriteRecipeRepo
) : ViewModel() {

    private val _meals: MutableLiveData<UiState<List<Meals>>> = MutableLiveData(UiState.Loading)
    val meals: LiveData<UiState<List<Meals>>> = _meals
    var favoriteUserIds = MediatorLiveData<List<String>>()

    init {
        viewModelScope.launch {
//
//            favoriteRecipeRepo.getRecipes()
//            delay(300)
            favoriteUserIds.addSource(favoriteRecipeRepo.favoriteRecipeIDs) {
                Log.i("ya rab", ": " + it.size)
                favoriteUserIds.postValue(it)
            }
        }
    }

    fun getMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = async { repository.getMeals() }.await()

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


    fun addFavoriteRecipe(meal: Meals) {
        if (meal != null) {

            viewModelScope.launch(Dispatchers.IO) {

                Log.i("addeddddd", "yes 2")
                meal.count += 1
                favoriteRecipeRepo.addFavouriteRecipe(meal)
            }

        }
    }

    fun deleteFavoriteRecipe(meal: Meals) {
        if (meal != null) {

            viewModelScope.launch(Dispatchers.IO) {
                meal.count -= 1
                favoriteRecipeRepo.deleteFavouriteRecipeList(meal)
            }
        }
    }

}

class ProductViewModelFactory(
    private val repository: MealsRepo,
    private val favoriteRecipeRepo: FavoriteRecipeRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeFragmentViewModel(repository, favoriteRecipeRepo) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

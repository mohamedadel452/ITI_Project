package com.example.iti_project.ui.RecipeActivity.Favourit

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFragmentViewModel(
    private val favoriteRecipeRepo: FavoriteRecipeRepo
) : ViewModel() {

    var favoriteRecipes =  MediatorLiveData<List<Meals>>()
    var favoriteUserIds =  MediatorLiveData<List<String>>()

    init {

        viewModelScope.launch {
//
//            favoriteRecipeRepo.getRecipes()

            favoriteUserIds.addSource(favoriteRecipeRepo.favoriteRecipeIDs){
                favoriteUserIds.postValue(it)

            }

            favoriteRecipes.addSource(favoriteRecipeRepo.favoriteRecipe){response ->
                favoriteRecipes.postValue(response)
            }
        }

    }


    fun deleteFavoriteRecipe(meals: Meals) {
        if (meals != null) {

            viewModelScope.launch(Dispatchers.IO) {
                favoriteRecipeRepo.deleteFavouriteRecipeList(meals)
            }
        }
    }

}

class FavoriteFragmentViewModelFactory(private val repository: FavoriteRecipeRepo) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteFragmentViewModel::class.java)
        ) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteFragmentViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
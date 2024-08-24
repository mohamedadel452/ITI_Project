package com.example.iti_project.ui.RecipeActivity.Favourit

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.repo.Meals.MealsRepo
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepo
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepoImp
import com.example.iti_project.ui.RecipeActivity.homeFragment.HomeFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoriteFragmentViewModel(
    private val favoriteRecipeRepo: FavoriteRecipeRepo
) : ViewModel() {

    var favoriteRecipes =  MediatorLiveData<List<Meals>>()
    var favoriteUserIds: MutableList<String> = mutableListOf()

    init {

        viewModelScope.launch {
//
//            favoriteRecipeRepo.getRecipes()
            delay(300)
            favoriteRecipes.addSource(favoriteRecipeRepo.favoriteRecipe){
                Log.i("ya rab", ": "+it.size)
                favoriteRecipes.postValue(it)
            }
//            favoriteUserIds = favoriteRecipeRepo.favoriteRecipeIDs.toMutableList()
////            Log.i("favoriteRecipes", ""+favoriteRecipes.size)
        }

    }


    fun deleteFavoriteRecipe(id: String) {
        if (id != null) {

            GlobalScope.launch(Dispatchers.IO) {
                favoriteRecipeRepo.deleteFavouriteRecipeList(id)

            }
        }
    }

    //    fun  getFavoriteList(){
//        GlobalScope.launch(Dispatchers.IO) {
//
//            repository.getRecipes()
//            delay(200)
////            favoriteRecipes = repository.favoriteRecipe.toMutableList()
//            favoriteUserIds = repository.favoriteRecipeIDs.toMutableList()
//            Log.i("favoriteRecipes", ""+favoriteRecipes.size)
//        }
//    }
    fun getFavoriteList() {
//        viewModelScope.launch(Dispatchers.IO) {

//            favoriteRecipes.addSource(favoriteRecipeRepo.getRecipes()){
//                favoriteRecipes.postValue(it)
//            }
//            delay(2000)
//            favoriteUserIds = favoriteRecipeRepo.favoriteRecipeIDs.toMutableList()
//            Log.i("favoriteRecipes", ""+favoriteRecipes.size)
//        }
    }
}

class FavoriteFragmentViewModelFactory(private val repository: FavoriteRecipeRepo) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFragmentViewModel::class.java) ||
            modelClass.isAssignableFrom(FavoriteFragmentViewModel::class.java)
        ) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteFragmentViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
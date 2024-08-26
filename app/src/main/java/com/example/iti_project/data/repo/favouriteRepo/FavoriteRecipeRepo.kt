package com.example.iti_project.data.repo.favouriteRepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.UserModel

interface FavoriteRecipeRepo {

    val favoriteRecipe: LiveData<List<Meals>>
    val favoriteRecipeIDs: LiveData<List<String>>
    suspend fun addFavouriteRecipe(meal: Meals): Long

//    suspend fun getFavouriteRecipe(id : String) : Meals

    suspend fun deleteFavouriteRecipeList(meals: Meals)

    //    fun getRecipes() :LiveData<List<Meals>>
    fun getStatusOfRecipe(id: String): Boolean
}
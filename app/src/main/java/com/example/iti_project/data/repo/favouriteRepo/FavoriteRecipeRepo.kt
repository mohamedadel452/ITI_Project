package com.example.iti_project.data.repo.favouriteRepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.UserModel

interface FavoriteRecipeRepo {

    val favoriteRecipe: List<Meals>
    val favoriteRecipeIDs: List<String>
    suspend fun addFavouriteRecipe(meal: Meals): Long

//    suspend fun getFavouriteRecipe(id : String) : Meals

    suspend fun deleteFavouriteRecipeList(id : String) : Int
    suspend fun getRecipes()
}
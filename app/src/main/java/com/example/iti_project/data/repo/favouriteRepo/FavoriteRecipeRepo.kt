package com.example.iti_project.data.repo.favouriteRepo

import androidx.lifecycle.LiveData
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.UserModel

interface FavoriteRecipeRepo {

    val favoriteRecipe: LiveData<MutableList<Meals>>

    suspend fun addFavouriteRecipe(meal: Meals): Long

//    suspend fun getFavouriteRecipe(id : String) : Meals

    suspend fun deleteFavouriteRecipeList(id : String) : Int
}
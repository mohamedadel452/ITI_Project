package com.example.iti_project.data.DataSource.LocalDataSource.InterFace

import androidx.lifecycle.LiveData
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.UserModel

interface LocalDataSource {

    //it will return -1 if the insert fails
    suspend fun insertUser(user: UserModel): Long

    //it will return null if the user is not found
    suspend fun getUserByEmail(email: String): UserModel?

    suspend fun getLoggedIn(): String

    //it will return true if it set the value successfully
    suspend fun setLoggedIn(email : String): Boolean

    suspend fun addFavouriteRecipe(favoriteID : String):Boolean

    suspend fun addFavouriteRecipeList(favorite : List<String>): Boolean
    suspend fun getFavouriteListByEmail(): MutableList<String>

    suspend fun addFavouriteRecipe(meal: Meals): Long

    suspend fun getFavouriteRecipe(id : String) :Meals

    suspend fun deleteFavouriteRecipeList(id : String) : Int
}
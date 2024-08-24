package com.example.iti_project.data.DataSource.LocalDataSource.InterFace

import androidx.lifecycle.LiveData
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.UserModel

interface LocalDataSource {

    //it will return -1 if the insert fails
    suspend fun insertUser(user: UserModel): Long

    //it will return null if the user is not found
    fun getUserByEmail(email: String): UserModel?

    fun getLoggedIn(): String

    //it will return true if it set the value successfully
    fun setLoggedIn(email : String): Boolean

    suspend fun addFavouriteRecipe(favoriteID : String):Boolean

    suspend fun addFavouriteRecipeList(favorite : List<String>): Boolean
    fun getFavouriteListByEmail(): LiveData<List<String>>

    suspend fun addFavouriteRecipe(meal: Meals): Long

    fun getFavouriteRecipe(id : List<String>): LiveData<List<Meals>>

    suspend fun deleteFavouriteRecipeList(meal: Meals, isWantToDelete: Boolean = false) : Int
}
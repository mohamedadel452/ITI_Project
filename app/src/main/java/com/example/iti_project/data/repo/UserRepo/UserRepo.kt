package com.example.iti_project.data.repo.UserRepo

import androidx.lifecycle.LiveData
import com.example.iti_project.data.models.UserModel

interface UserRepo {


    //it will return -1 if the insert fails
    suspend fun insertUser(user: UserModel): Long


    //it will return null if the user is not found
    fun getUserByEmail(email: String): UserModel?


    suspend fun getLoggedIn(): String


    //returns true if changes were successfully written to persistent storage
    fun setLoggedIn(email: String): Boolean

    suspend fun addFavouriteRecipe(favoriteID : String) : Boolean

    fun getFavouriteRecipeList() : LiveData<List<String>>
}
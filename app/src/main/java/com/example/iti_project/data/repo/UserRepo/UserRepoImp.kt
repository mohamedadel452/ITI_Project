package com.example.iti_project.data.repo.UserRepo

import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSource
import com.example.iti_project.data.models.UserModel

class  UserRepoImp(
    private   var  localdata : LocalDataSource?,

    ) :UserRepo {
    override suspend fun insertUser(user: UserModel): Long {
        return localdata?.insertUser(user)?:-1L
    }

    override suspend fun getUserByEmail(email: String): UserModel? {
        return localdata?.getUserByEmail(email)
    }

    override suspend fun getLoggedIn(): String {
        return localdata?.getLoggedIn() ?: "Not Found"
    }

    //returns true if changes were successfully written to persistent storage
    override suspend fun setLoggedIn(email : String):Boolean {
        return localdata?.setLoggedIn(email)?:false

    }

    override suspend fun addFavouriteRecipe(favoriteID : String): Boolean {
        return  localdata?.addFavouriteRecipe(favoriteID) ?: false
    }

    override suspend fun getFavouriteRecipeList(): MutableList<String> {
        return localdata?.getFavouriteListByEmail() ?: mutableListOf()
    }
}
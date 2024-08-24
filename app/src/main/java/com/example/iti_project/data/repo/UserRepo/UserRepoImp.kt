package com.example.iti_project.data.repo.UserRepo

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSource
import com.example.iti_project.data.models.UserModel

class  UserRepoImp(
    private   var  localdata : LocalDataSource,

    ) :UserRepo {
    override suspend fun insertUser(user: UserModel): Long {
        return localdata.insertUser(user)?:-1L
    }

    override fun getUserByEmail(email: String): UserModel? {
        return localdata.getUserByEmail(email)
    }

    override suspend fun getLoggedIn(): String {
        return localdata.getLoggedIn()
    }

    //returns true if changes were successfully written to persistent storage
    override fun setLoggedIn(email : String):Boolean {
        return localdata.setLoggedIn(email)

    }

    override suspend fun addFavouriteRecipe(favoriteID : String): Boolean {
        return  localdata.addFavouriteRecipe(favoriteID) ?: false
    }

    override fun getFavouriteRecipeList(): LiveData<List<String>> {
        return localdata.getFavouriteListByEmail()
    }
}
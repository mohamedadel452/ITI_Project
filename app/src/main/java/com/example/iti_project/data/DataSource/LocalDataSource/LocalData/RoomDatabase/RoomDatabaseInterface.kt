package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase

import androidx.lifecycle.LiveData
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.UserModel

interface RoomDatabaseInterface {


     suspend fun insertUser(user: UserModel): Long

     fun getUserByEmail(email: String): UserModel?

     suspend fun addFavouriteItem(user:UserModel): Int

     fun getFavouriteListByEmail(email: String):LiveData<List<String>>

     fun addFavouriteRecipe(meal: Meals): Long

     fun getFavouriteRecipe(id : List<String>) : LiveData<List<Meals>>

     fun getFavouriteRecipeCount(id : String) : Int

     suspend fun deleteFavouriteRecipeList(id : String) : Int


}
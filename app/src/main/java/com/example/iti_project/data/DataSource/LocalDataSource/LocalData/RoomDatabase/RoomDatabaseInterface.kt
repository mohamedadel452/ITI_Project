package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase

import androidx.lifecycle.LiveData
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.UserModel

interface RoomDatabaseInterface {


     suspend fun insertUser(user: UserModel): Long

     suspend fun getUserByEmail(email: String): UserModel?

     suspend fun addFavouriteItem(user:UserModel): Int

     suspend fun getFavouriteListByEmail(email: String): MutableList<String>

     suspend fun addFavouriteRecipe(meal: Meals): Long

     suspend fun getFavouriteRecipe(id : List<String>) : List <Meals>

     suspend fun deleteFavouriteRecipeList(id : String) : Int

//     suspend fun getAllRecipes(): LiveData<List<Meal>>
//
//     suspend fun getRecipeDetails(id :String): RecipesModel?
//
//     suspend fun insertRecipesInfo(recipesModel :List<RecipesModel>)

}
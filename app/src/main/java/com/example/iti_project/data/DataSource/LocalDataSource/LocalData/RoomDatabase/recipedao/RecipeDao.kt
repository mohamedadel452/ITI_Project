package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.recipedao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.iti_project.data.models.UserModel

@Dao
interface RecipeDao {

//    @Query("SELECT * FROM recipes")
//    suspend fun getAllRecipes(): LiveData<List<RecipesModel>>
//
//    @Query("SELECT * FROM recipes WHERE id = :id")
//    suspend fun getRecipeDetails(id :String): RecipesModel?
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertRecipesInfo(recipesModel :List<RecipesModel>)

}
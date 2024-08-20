package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.favoriteDao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.iti_project.data.models.Meals

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteRecipe(meal: Meals): Long
    @Query("SELECT * FROM meals WHERE idMeal = :id")
    suspend fun getFavouriteRecipe(id : String) : Meals

    @Delete
    suspend fun deleteFavouriteRecipeList(id : String) : Int
}
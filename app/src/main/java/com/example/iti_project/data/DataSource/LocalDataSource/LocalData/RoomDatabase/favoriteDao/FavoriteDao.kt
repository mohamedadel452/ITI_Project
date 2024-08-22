package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.favoriteDao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.iti_project.data.models.Meals

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteRecipe(meal: Meals): Long
    @Query("SELECT * FROM meals WHERE idMeal in (:id)")
    fun getFavouriteRecipe(id : List<String>) : List<Meals>

    @Query("DELETE FROM meals WHERE idMeal=:id")
    suspend fun deleteFavouriteRecipeList(id : String) : Int
}
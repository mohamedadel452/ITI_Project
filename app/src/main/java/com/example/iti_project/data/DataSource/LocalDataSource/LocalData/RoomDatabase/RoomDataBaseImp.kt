package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.favoriteDao.FavoriteDao
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.userdao.UserDao
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.MealsResponse
import com.example.iti_project.data.models.UserModel


@Database(entities = [UserModel::class , Meals::class], version = 3 , exportSchema = true)
@TypeConverters(Converters::class)
abstract class RoomDataBaseImp :RoomDatabaseInterface, RoomDatabase(){

    abstract fun getUserDao(): UserDao
    abstract fun getFavoriteDao(): FavoriteDao
    companion object{

        @Volatile
        private var instance: RoomDataBaseImp? = null

        fun getInstance(context: Context): RoomDataBaseImp {

            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    RoomDataBaseImp::class.java,
                    "room_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }

    //it will return -1 if the insertion failed
    override  suspend fun insertUser(user: UserModel): Long {
        return getUserDao().insertNewUser(user)
    }

    //it will return null if the user not found
    override suspend fun getUserByEmail(email: String): UserModel? {
        return getUserDao().getUserByEmail(email)
    }

    override suspend fun getFavouriteListByEmail(email: String): MutableList<String> {
        return getUserDao().getFavouriteListByEmail(email)
    }

    override suspend fun addFavouriteRecipe(meal: Meals): Long{
        return getFavoriteDao().addFavouriteRecipe(meal)
    }

    override suspend fun getFavouriteRecipe(id : List<String>) : List<Meals> {
        return getFavoriteDao().getFavouriteRecipe(id)
    }

    override suspend fun deleteFavouriteRecipeList(id : String) : Int{
        return getFavoriteDao().deleteFavouriteRecipeList(id)
    }

//    override suspend fun getAllRecipes(): LiveData<List<RecipesModel>> {
//
//    }
//
//    override suspend fun getRecipeDetails(id: String): RecipesModel? {
//
//    }
//
//    override suspend fun insertRecipesInfo(recipesModel: List<RecipesModel>) {
//
//    }
    override suspend fun addFavouriteItem(user:UserModel): Int {
        return getUserDao().addFavouriteItem(user)
    }
}
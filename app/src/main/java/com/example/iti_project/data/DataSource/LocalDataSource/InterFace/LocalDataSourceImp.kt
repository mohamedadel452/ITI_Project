package com.example.iti_project.data.DataSource.LocalDataSource.InterFace

import android.content.Context
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDataBaseImp
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDatabaseInterface
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceInterface
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.UserModel

class LocalDataSourceImp(
    context: Context,
    val roomDataSource: RoomDatabaseInterface,
    val sharedPreferencesDataSource: SharedPreferenceInterface?
) : LocalDataSource {

    override suspend fun insertUser(user: UserModel): Long {
        roomDataSource.let { return roomDataSource.insertUser(user) }

    }

    override suspend fun getUserByEmail(email: String): UserModel? {
        roomDataSource.let { return roomDataSource.getUserByEmail(email) }
    }


    override suspend fun getLoggedIn(): String {
        return sharedPreferencesDataSource?.getLoggedIn() ?: "Not Found"
    }

    //returns true if changes were successfully written to persistent storage
    override suspend fun setLoggedIn(email: String): Boolean {
        return sharedPreferencesDataSource?.setLoggedIn(email) ?: false
    }

    override suspend fun addFavouriteRecipe(favoriteID: String): Boolean {
        val email = getLoggedIn()
        Log.i("email", email)
        val user = getUserByEmail(email)
        Log.i("email", " "+user?.email)
        return if (user != null && !user.favoriteID.contains(favoriteID)) {
            user.favoriteID.add(favoriteID)
            Log.i("email", favoriteID)
            roomDataSource.addFavouriteItem(user) != -1
        } else {
            false
        }
    }

    override suspend fun addFavouriteRecipeList(favorite: List<String>): Boolean {
        val email = getLoggedIn()
//        Log.i("favoriteID", email)
        val user = getUserByEmail(email)

        return if (user != null) {
            user.favoriteID = favorite.toMutableList()
//            listenOnChangeRecipe(favoriteRecipeID: String)
            roomDataSource.addFavouriteItem(user) != -1
        } else {
            false
        }
    }


    override suspend fun getFavouriteListByEmail(): MutableList<String> {
        val email = getLoggedIn()
        val user = getUserByEmail(email)
        return if (user != null) {
            user.favoriteID
        } else {
            mutableListOf()
        }
    }

    override suspend fun addFavouriteRecipe(meal: Meals): Long {
        addFavouriteRecipe(meal.idMeal)
        return roomDataSource.addFavouriteRecipe(meal) ?: -1L
    }

    override suspend fun getFavouriteRecipe(id: String): Meals {
        return roomDataSource.getFavouriteRecipe(id)
    }

    override suspend fun deleteFavouriteRecipeList(id: String): Int {
        return roomDataSource.deleteFavouriteRecipeList(id)
    }
}


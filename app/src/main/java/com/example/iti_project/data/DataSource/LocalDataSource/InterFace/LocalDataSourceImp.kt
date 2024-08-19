package com.example.iti_project.data.DataSource.LocalDataSource.InterFace

import android.util.Log
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDatabaseInterface
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceInterface
import com.example.iti_project.data.models.UserModel

class LocalDataSourceImp(
    val roomDataSource: RoomDatabaseInterface?,
    val sharedPreferencesDataSource: SharedPreferenceInterface?
) : LocalDataSource {

    override suspend fun insertUser(user: UserModel): Long {
        roomDataSource?.let { return roomDataSource.insertUser(user) }
            ?: return -1
    }

    override suspend fun getUserByEmail(email: String): UserModel? {
        roomDataSource?.let { return roomDataSource.getUserByEmail(email) }
            ?: return null
    }

    override suspend fun getLoggedIn(): String {
        return sharedPreferencesDataSource?.getLoggedIn() ?: "Not Found"
    }

    //returns true if changes were successfully written to persistent storage
    override suspend fun setLoggedIn(email: String): Boolean {
        return sharedPreferencesDataSource?.setLoggedIn(email) ?: false
    }

    override suspend fun addFavouriteRecipe(favoriteID: String): Boolean {
        val email =  getLoggedIn()
        Log.i("favoriteID", email)
        val user = getUserByEmail(email)
        Log.i("favoriteID", user?.userName ?: "    sdasds  ")
        return if( user != null) {
            user.favoriteID.add(favoriteID)
            Log.i("favoriteID", roomDataSource?.addFavouriteItem(user).toString())
            true
        }else{
            false
        }
    }
}

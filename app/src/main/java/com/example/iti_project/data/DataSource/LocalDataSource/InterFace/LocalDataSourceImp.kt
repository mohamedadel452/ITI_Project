package com.example.iti_project.data.DataSource.LocalDataSource.InterFace

import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDatabaseInterface
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceInterface
import com.example.iti_project.data.models.UserModel

class LocalDataSourceImp(
    val roomDataSource: RoomDatabaseInterface? ,
    val sharedPreferencesDataSource: SharedPreferenceInterface?
):LocalDataSource {

    override suspend fun insertUser(user: UserModel): Long {
        roomDataSource?.let { return roomDataSource.insertUser(user) }
            ?:return -1
    }

    override  suspend fun getUserByEmail(email: String): UserModel? {
        roomDataSource?.let { return roomDataSource.getUserByEmail(email) }
            ?:return null
    }

    override suspend fun isLoggedIn(): Boolean {
        return sharedPreferencesDataSource?.isLoggedIn() ?: false
    }


    //returns true if changes were successfully written to persistent storage
    override suspend fun setLoggedIn(value: Boolean): Boolean {
        return sharedPreferencesDataSource?.setLoggedIn(value)?:false

        }
    }

package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.concurrent.Volatile

class SharedPreferenceImp private constructor(val context: Context) : SharedPreferenceInterface {

    private val sharedPreference: SharedPreferences =
        context.getSharedPreferences("login", Context.MODE_PRIVATE)


    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: SharedPreferenceImp? = null
        fun getInstance(context: Context): SharedPreferenceImp {
            return instance ?: synchronized(this) {
                instance ?: SharedPreferenceImp(context.applicationContext).also {
                    instance = it
                }
            }
        }


    }


    override suspend fun setLoggedIn(email : String): Boolean {

        return withContext(Dispatchers.IO) {
            sharedPreference.edit()
                .putString("login", email)
                .commit() // commit returns true if changes were successfully written to persistent storage

        }
    }

    override suspend fun getLoggedIn(): String? {
        return withContext(Dispatchers.IO) {
            sharedPreference.getString("login" , "Not Found")
        }
    }
}

package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
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


    override fun setLoggedIn(email : String): Boolean {
//        Log.i("set log", email)
        return sharedPreference.edit()
                    .putString("login", email)
                    .commit() // commit returns true if changes were successfully written to persistent storage
    }

    override fun getLoggedIn(): String? {
        return sharedPreference.getString("login" , "Not Found")
    }
}

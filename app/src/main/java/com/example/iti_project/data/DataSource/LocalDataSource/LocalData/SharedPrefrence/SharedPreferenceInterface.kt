package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence

interface SharedPreferenceInterface {

    fun setLoggedIn(email : String): Boolean

    suspend fun getLoggedIn(): String?
}
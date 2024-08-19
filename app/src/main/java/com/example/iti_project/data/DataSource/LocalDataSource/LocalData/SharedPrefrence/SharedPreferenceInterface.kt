package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence

interface SharedPreferenceInterface {

    suspend fun isLoggedIn(): Boolean

    suspend fun setLoggedIn(value: Boolean ,email : String): Boolean

    suspend fun getLoggedIn(): String?
}
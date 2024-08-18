package com.example.iti_project.data.DataSource.LocalDataSource.InterFace

import com.example.iti_project.data.models.UserModel

interface LocalDataSource {

    //it will return -1 if the insert fails
     suspend fun insertUser(user: UserModel): Long


    //it will return null if the user is not found
    suspend fun getUserByEmail(email: String): UserModel?


    suspend fun isLoggedIn(): Boolean


    //it will return true if it set the value successfully
    suspend fun setLoggedIn(value: Boolean): Boolean



}
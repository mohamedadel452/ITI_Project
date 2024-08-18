package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase

import com.example.iti_project.data.models.UserModel

interface RoomDatabaseInterface {


     suspend fun  insertUser(user: UserModel): Long

     suspend fun getUserByEmail(email: String): UserModel?



}
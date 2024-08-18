package com.example.iti_project.data.DataSource.LocalDataSource.InterFace

import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDatabaseInterface
import com.example.iti_project.data.models.UserModel

class LocalDataSourceImp(
    val roomDataSource: RoomDatabaseInterface?
):LocalDataSource {

    override suspend fun insertUser(user: UserModel): Long {
        roomDataSource?.let { return roomDataSource.insertUser(user) }
            ?:return -1
    }

    override  suspend fun getUserByEmail(email: String): UserModel? {
        roomDataSource?.let { return roomDataSource.getUserByEmail(email) }
            ?:return null
    }
}
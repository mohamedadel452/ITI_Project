package com.example.iti_project.data.repo.UserRepo

import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSource
import com.example.iti_project.data.models.UserModel

class  UserRepoImp(
    private   var    roomdata : LocalDataSource
) :UserRepo {
    override suspend fun insertUser(user: UserModel): Long {
       return roomdata.insertUser(user)
    }

    override suspend fun getUserByEmail(email: String): UserModel? {
        return roomdata.getUserByEmail(email)
    }


}
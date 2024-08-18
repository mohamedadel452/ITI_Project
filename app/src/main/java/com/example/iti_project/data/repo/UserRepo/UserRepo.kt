package com.example.iti_project.data.repo.UserRepo

import com.example.iti_project.data.models.UserModel

interface UserRepo {


    //it will return -1 if the insert fails
    suspend fun insertUser(user: UserModel): Long


    //it will return null if the user is not found
    suspend fun getUserByEmail(email: String): UserModel?

}
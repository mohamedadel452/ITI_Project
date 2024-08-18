package com.example.iti_project.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Users")
data class UserModel (

    @PrimaryKey val email: String,
    val userName: String,
    val password: String

)
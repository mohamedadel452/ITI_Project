package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.userdao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.iti_project.data.models.UserModel


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewUser(user: UserModel): Long

    @Query("SELECT * FROM users WHERE email = :email")
    suspend  fun getUserByEmail(email: String): UserModel?



}
package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.iti_project.data.models.UserModel


@Database(entities = [UserModel::class], version = 2)
abstract class RoomDataBaseImp :RoomDatabaseInterface, RoomDatabase(){

    abstract fun getUserDao(): UserDao

    companion object{

        @Volatile
        private var instance: RoomDataBaseImp? = null

        fun getInstance(context: Context): RoomDataBaseImp {

            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    RoomDataBaseImp::class.java,
                    "room_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }

    //it will return -1 if the insertion failed
    override  suspend fun insertUser(user: UserModel): Long {
        return getUserDao().insertNewUser(user)
    }

    //it will return null if the user not found
    override suspend  fun getUserByEmail(email: String): UserModel? {

        return getUserDao().getUserByEmail(email)

    }
}
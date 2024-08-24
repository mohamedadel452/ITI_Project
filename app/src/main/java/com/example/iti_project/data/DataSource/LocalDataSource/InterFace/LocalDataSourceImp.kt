package com.example.iti_project.data.DataSource.LocalDataSource.InterFace

import android.content.Context
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDataBaseImp
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDatabaseInterface
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceInterface
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.UserModel

class LocalDataSourceImp(
    context: Context,
    val roomDataSource: RoomDatabaseInterface,
    val sharedPreferencesDataSource: SharedPreferenceInterface?
) : LocalDataSource {

    override suspend fun insertUser(user: UserModel): Long {
        roomDataSource.let { return roomDataSource.insertUser(user) }

    }

    override fun getUserByEmail(email: String): UserModel? {
        roomDataSource.let { return roomDataSource.getUserByEmail(email) }
    }


    override fun getLoggedIn(): String {
        return sharedPreferencesDataSource?.getLoggedIn() ?: "Not Found"
    }

    //returns true if changes were successfully written to persistent storage
    override fun setLoggedIn(email: String): Boolean {
//        Log.i("set log", email)
        return sharedPreferencesDataSource?.setLoggedIn(email) ?: false
    }

    override suspend fun addFavouriteRecipe(favoriteID: String): Boolean {
        val email = getLoggedIn()
//        Log.i("email", email)
        val user = getUserByEmail(email)
//        Log.i("email", " "+user?.email)
        return if (user != null && !user.favoriteID.contains(favoriteID)) {
            val list = user.favoriteID.toMutableList()
            list.add(favoriteID)
            user.favoriteID = list
//            Log.i("email sd", user.favoriteID[0])
            roomDataSource.addFavouriteItem(user) != -1
        } else {
            false
        }
    }

    override suspend fun addFavouriteRecipeList(favorite: List<String> ): Boolean {
        val email = getLoggedIn()
//        Log.i("favoriteID", email)
        val user = getUserByEmail(email)

        return if (user != null) {
            user.favoriteID = favorite
//            Log.i("add", favorite[0])
//            listenOnChangeRecipe(favoriteRecipeID: String)
            roomDataSource.addFavouriteItem(user) != -1
        } else {
            false
        }
    }


    override fun getFavouriteListByEmail(): LiveData<List<String>> {
        val email = getLoggedIn()
        return  roomDataSource.getFavouriteListByEmail(email)
    }

    override suspend fun addFavouriteRecipe(meal: Meals): Long {
        addFavouriteRecipe(meal.idMeal)
        return roomDataSource.addFavouriteRecipe(meal) ?: -1L
    }

    override fun getFavouriteRecipe(id: List<String>): LiveData<List<Meals>> {
        return roomDataSource.getFavouriteRecipe(id)
    }

    override suspend fun deleteFavouriteRecipeList(meal: Meals ): Int {

        return if (meal.count < 1){
            roomDataSource.deleteFavouriteRecipeList(meal.idMeal)
        }else{
            roomDataSource.addFavouriteRecipe(meal).toInt()
        }

    }

    override fun getFavouriteRecipeCount(id : String) : Int {
        return roomDataSource.getFavouriteRecipeCount(id)
    }

}


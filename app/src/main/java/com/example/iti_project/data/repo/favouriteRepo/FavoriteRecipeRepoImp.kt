package com.example.iti_project.data.repo.favouriteRepo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSource
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoriteRecipeRepoImp(
    private var localdata: LocalDataSource,

    ) : FavoriteRecipeRepo {


    private var _favoriteRecipe: MutableList<Meals> = mutableListOf()
    override val favoriteRecipe: List<Meals>
        get() = _favoriteRecipe

    private var _favoriteRecipeIDs: MutableList<String> = mutableListOf()
    override val favoriteRecipeIDs: List<String>
        get() = _favoriteRecipeIDs

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val favoriteList = async { localdata.getFavouriteListByEmail() }
            _favoriteRecipeIDs = favoriteList.await()
//            Log.i("email", " " + favoriteRecipeIDs?.first())
        }
    }
    override suspend fun getRecipes() {

//            for (favoriteRecipeID in _favoriteRecipeIDs) {

                Log.i("email", " " + "52765")
                val favoriteRecipeResult: Meals =localdata.getFavouriteRecipe("52765")
//
                if (favoriteRecipeResult != null) {
                    Log.i("emailsa", " " + _favoriteRecipe.size)
                    _favoriteRecipe.add(favoriteRecipeResult)
                    Log.i("favo", _favoriteRecipe?.first()?.strArea ?: " sfdsdf ")
                }


//            }


    }


    fun updateFavoriteRecipe(meal: Meals) {
        _favoriteRecipe.add(meal)
    }

    override suspend fun addFavouriteRecipe(meal: Meals): Long {
        updateFavoriteRecipe(meal)
        var isSuccess: Long = 0
        GlobalScope.launch(Dispatchers.IO) { isSuccess = localdata.addFavouriteRecipe(meal) }
        return isSuccess
    }

    override suspend fun deleteFavouriteRecipeList(id: String): Int {
        val mutableList = localdata.getFavouriteListByEmail()
        mutableList.remove(id)
        deleteFromFavoriteRecipe(id)
        localdata.addFavouriteRecipeList(mutableList)
        return localdata.deleteFavouriteRecipeList(id)
    }

    private fun deleteFromFavoriteRecipe(id: String) {
        for (i in favoriteRecipe) {
            if (i.idMeal == id) {
                _favoriteRecipe.remove(i)
                break
            }
        }
    }

}
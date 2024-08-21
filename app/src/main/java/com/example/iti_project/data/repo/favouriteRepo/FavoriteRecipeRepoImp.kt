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
import kotlinx.coroutines.launch

class FavoriteRecipeRepoImp(
    private var localdata: LocalDataSource,

    ) : FavoriteRecipeRepo {


    private var _favoriteRecipe : MutableList<Meals> = mutableListOf()
    override val favoriteRecipe: List<Meals>
        get() = _favoriteRecipe

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val favoriteList = async { localdata.getFavouriteListByEmail() }
            val favoriteRecipesList = favoriteList.await()

            for (favoriteRecipe in favoriteRecipesList) {

                val favoriteRecipeResult: Meals = async {  localdata.getFavouriteRecipe(favoriteRecipe)}.await()

                if (favoriteRecipeResult != null) {
                    _favoriteRecipe.add(favoriteRecipeResult)
//                    Log.i("favo", _favoriteRecipe?.first()?.strArea ?: " sfdsdf ")
                }

            }


        }
    }

    fun updateFavoriteRecipe(meal : Meals){
        _favoriteRecipe.add(meal)
    }
    override suspend fun addFavouriteRecipe(meal: Meals): Long {
        updateFavoriteRecipe(meal)
        return localdata.addFavouriteRecipe(meal) ?: -1L
    }

    override suspend fun deleteFavouriteRecipeList(id: String): Int {
        val mutableList = localdata.getFavouriteListByEmail()
        mutableList.remove(id)
        deleteFromFavoriteRecipe(id)
        localdata.addFavouriteRecipeList(mutableList)
        return localdata.deleteFavouriteRecipeList(id)
    }
    fun deleteFromFavoriteRecipe(id : String){
        for (i in favoriteRecipe) {
            if (i.idMeal == id) {
                _favoriteRecipe.remove(i)
                break
            }
        }
    }

}
package com.example.iti_project.data.repo.favouriteRepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSource
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class  FavoriteRecipeRepoImp(
    private   var  localdata : LocalDataSource,

    ) :FavoriteRecipeRepo {

    private val _favoriteRecipe = MediatorLiveData<MutableList<Meals>>()
    override val favoriteRecipe: LiveData<MutableList<Meals>>
        get() = _favoriteRecipe

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val favoriteList = async{localdata.getFavouriteListByEmail()}
            val favoriteRecipesList = favoriteList.await()
            val favoriteRecipeList :  MutableList<Meals> = mutableListOf()
            for (favoriteRecipe in favoriteRecipesList) {
                favoriteRecipeList.add( localdata.getFavouriteRecipe(favoriteRecipe))
            }
            _favoriteRecipe.postValue(
               favoriteRecipeList)

        }
    }

    suspend fun listenOnChangeRecipe(favoriteRecipeID: String) {
        GlobalScope.launch(Dispatchers.IO) {
            var newfavoriteRecipe: Meals
            newfavoriteRecipe = localdata.getFavouriteRecipe(favoriteRecipeID)
            if (newfavoriteRecipe != null) {
                _favoriteRecipe.addSource(favoriteRecipe) { favoriteRecipe ->
                    favoriteRecipe.add(newfavoriteRecipe)
                    _favoriteRecipe.postValue(favoriteRecipe)
                }
            }

        }
    }



    override suspend fun addFavouriteRecipe(meal: Meals): Long {
        return localdata.addFavouriteRecipe(meal)?: -1L
    }

    override suspend fun deleteFavouriteRecipeList(id: String): Int {
        var mutableList =  localdata.getFavouriteListByEmail()
        mutableList.remove(id)
        localdata.addFavouriteRecipeList(mutableList)
        return localdata.deleteFavouriteRecipeList(id)
    }


}
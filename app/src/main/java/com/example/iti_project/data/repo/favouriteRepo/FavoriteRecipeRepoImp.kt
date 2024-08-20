package com.example.iti_project.data.repo.favouriteRepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSource
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.UserModel
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
            if (favoriteRecipesList != null) {
                val favoriteRecipeList :  MutableList<Meals> = mutableListOf()
                for (favoriteRecipe in favoriteRecipesList) {
                    favoriteRecipeList.add( localdata.getFavouriteRecipe(favoriteRecipe))
                }
                 _favoriteRecipe.value =
                    favoriteRecipeList

            }
        }
    }

    override suspend fun fetchData() {

    }



    override suspend fun addFavouriteRecipe(meal: Meals): Long {
        return localdata.addFavouriteRecipe(meal)?: -1L
    }

    override suspend fun getFavouriteRecipe(id: String): Meals {
        return localdata.getFavouriteRecipe(id) ?: null
    }

    override suspend fun deleteFavouriteRecipeList(id: String): Int {
        TODO("Not yet implemented")
    }


}
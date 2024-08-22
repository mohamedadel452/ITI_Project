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


    private var _favoriteRecipe: MediatorLiveData<List<Meals>> = MediatorLiveData<List<Meals>>(
        listOf())
    override val favoriteRecipe: LiveData<List<Meals>>
        get() = _favoriteRecipe

    private var _favoriteRecipeIDs: MutableList<String> = mutableListOf()
    override val favoriteRecipeIDs: List<String>
        get() = _favoriteRecipeIDs

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val favoriteList = async { localdata.getFavouriteListByEmail() }
            _favoriteRecipeIDs = favoriteList.await()
            Log.i("favoriteRecipes", ""+ favoriteRecipeIDs.size)
            Log.i("favoriteRecipes", ""+localdata.getFavouriteRecipe(favoriteRecipeIDs).value?.size)
//            _favoriteRecipe.addSource(localdata.getFavouriteRecipe(favoriteRecipeIDs)) { favoriteRecipe ->
//                if (favoriteRecipe.isNotEmpty()) _favoriteRecipe.postValue( favoriteRecipe)
//            }
        }
    }
    override suspend fun getRecipes()  {

        GlobalScope.launch(Dispatchers.IO) {
            val favoriteList = async { localdata.getFavouriteListByEmail() }
            _favoriteRecipeIDs = favoriteList.await()
            _favoriteRecipe.postValue( localdata.getFavouriteRecipe(favoriteRecipeIDs).value )
            Log.i("_favoriteRecipe", "  "+ _favoriteRecipe.value?.size)
        }
    }


    fun updateFavoriteRecipe(meal: Meals) {
        val list: MutableList<Meals>
        if (_favoriteRecipe.value  != null) {
            list = _favoriteRecipe.value as MutableList<Meals>
            list.add(meal)
            _favoriteRecipe.postValue(list)
        }else{
            list =  mutableListOf(meal)
            _favoriteRecipe.postValue(list)
        }
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

        val list: MutableList<Meals>
        if (_favoriteRecipe.value  != null) {
            list = _favoriteRecipe.value as MutableList<Meals>
            for (i in list) {
                if (i.idMeal == id) {
                    list.remove(i)
                    break
                }
            }
            _favoriteRecipe.postValue(list)
        }

    }

}
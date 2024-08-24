package com.example.iti_project.data.repo.favouriteRepo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.lifecycle.observe
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSource
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoriteRecipeRepoImp(
    private var localdata: LocalDataSource,

    ) : FavoriteRecipeRepo {


    private var _favoriteRecipe = MediatorLiveData<List<Meals>>()
    override val favoriteRecipe: LiveData<List<Meals>>
        get() = _favoriteRecipe

    private var _favoriteRecipeIDs: MutableList<String> = mutableListOf()
    override val favoriteRecipeIDs: List<String>
        get() = _favoriteRecipeIDs

    init {
        MainScope().launch(Dispatchers.IO) {
//            val Ob = Observer<List<Meals> > {  }
////
            _favoriteRecipeIDs = localdata.getFavouriteListByEmail()
            Log.i("favoriteRecipesIDS", "" + favoriteRecipeIDs.size)
////
////                favoriteRecipe = localdata.getFavouriteRecipe(favoriteRecipeIDs)
//        }

            _favoriteRecipe.addSource(localdata.getFavouriteRecipe(_favoriteRecipeIDs)) { favoriteRecipe ->
                if (favoriteRecipe.isNotEmpty()) _favoriteRecipe.postValue(favoriteRecipe)
                Log.i("favoriteRecipe", "" + favoriteRecipe.size)
            }
        }

    }

//    override fun getRecipes() {
//        MainScope().launch(Dispatchers.IO) {
//            _favoriteRecipeIDs = localdata.getFavouriteListByEmail()
////            _favoriteRecipe.postValue(  localdata.getFavouriteRecipe(favoriteRecipeIDs))
//            Log.i("_favoriteRecipe", "  " + _favoriteRecipeIDs.size)
////            delay(2000)
//            favoriteRecipe = localdata.getFavouriteRecipe(_favoriteRecipeIDs)
//
//            val y = Observer<List<Meals>> {
//                _favoriteRecipe.postValue(it)
//
//            }
//            favoriteRecipe.observe(this.coroutineContext.o , y)
//
//        }
//        Log.i("outer_favoriteRecipe", "  " + _favoriteRecipeIDs.size)
//
//    }


    override suspend fun addFavouriteRecipe(meal: Meals): Long {
        var isSuccess: Long = 0
        GlobalScope.launch(Dispatchers.IO) { isSuccess = localdata.addFavouriteRecipe(meal) }
        return isSuccess
    }

    override suspend fun deleteFavouriteRecipeList(id: String): Int {
        val mutableList = localdata.getFavouriteListByEmail()
        mutableList.remove(id)
//        deleteFromFavoriteRecipe(id)
        localdata.addFavouriteRecipeList(mutableList)

        return localdata.deleteFavouriteRecipeList(id)
    }

//    private fun deleteFromFavoriteRecipe(id: String) {
//
//        val list: MutableList<Meals>
//        if (_favoriteRecipe.value  != null) {
//            list = _favoriteRecipe.value as MutableList<Meals>
//            for (i in list) {
//                if (i.idMeal == id) {
//                    list.remove(i)
//                    break
//                }
//            }
//            _favoriteRecipe.postValue(list)
//        }
//
//    }

}
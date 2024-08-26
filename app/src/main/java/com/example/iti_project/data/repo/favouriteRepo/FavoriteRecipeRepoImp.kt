package com.example.iti_project.data.repo.favouriteRepo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSource
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.Converters
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

    private var _favoriteRecipe = MediatorLiveData<List<Meals>>()
    override val favoriteRecipe: LiveData<List<Meals>>
        get() = _favoriteRecipe

    var favoriteRecipeID: Int = 0


    private var _favoriteRecipeIDs = MediatorLiveData<List<String>>()
    override val favoriteRecipeIDs: LiveData<List<String>>
        get() = _favoriteRecipeIDs

    init {

        _favoriteRecipeIDs.addSource( localdata.getFavouriteListByEmail()) { response ->
//                Log.i("favoriteRecipesIDS viewModel", "    "  + Converters().fromStringToListOfStrings(response[0]).size )
            if (response.isNotEmpty()) {
                _favoriteRecipeIDs.postValue( Converters().fromStringToListOfStrings(response[0]))
                _favoriteRecipe.addSource(localdata.getFavouriteRecipe( Converters().fromStringToListOfStrings(response[0]))) { favoriteRecipe ->
                    if (favoriteRecipe.isNotEmpty()) _favoriteRecipe.postValue(favoriteRecipe)
//                        Log.i("favoriteRecipe", "" + favoriteRecipe.size)
                }
            }
        }

    }

    override suspend fun addFavouriteRecipe(meal: Meals): Long {

        favoriteRecipeID = localdata.getFavouriteRecipeCount(meal.idMeal)
        meal.count  = favoriteRecipeID + 1
        return localdata.addFavouriteRecipe(meal)
    }

    override suspend fun deleteFavouriteRecipeList(meal : Meals) {
        val favoriteIDList = favoriteRecipeIDs.value as MutableList<String>
        favoriteIDList.remove(meal.idMeal)
        favoriteRecipeID = localdata.getFavouriteRecipeCount(meal.idMeal)
        meal.count  = favoriteRecipeID - 1
//        deleteFromFavoriteRecipe(id)
        localdata.addFavouriteRecipeList(favoriteIDList )

        localdata.deleteFavouriteRecipeList(meal)
    }
    override fun getStatusOfRecipe(id: String): Boolean {

        val found : Boolean = favoriteRecipeIDs.value?.contains(id) == (true ?: false)
        return found

    }
}
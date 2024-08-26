package com.example.iti_project.ui.RecipeActivity.DetailsFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.MealsDetails
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.Meals.MealsRepo
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val repository: MealsRepo,
    private val favoriteRecipeRepo: FavoriteRecipeRepo
) : ViewModel() {

    private val _mealDetails = MutableLiveData<UiState<MealsDetails>>(UiState.Loading)
    val mealDetails: LiveData<UiState<MealsDetails>> = _mealDetails
    var favoriteUserIds = MediatorLiveData<List<String>>()
    private val _isFavorite = MutableLiveData<Boolean>()
    val isfavorite: LiveData<Boolean> = _isFavorite
    init {
        viewModelScope.launch {
            favoriteUserIds.addSource(favoriteRecipeRepo.favoriteRecipeIDs) {
                Log.i("teabt", ": " + it.size )
                favoriteUserIds.postValue(it)
            }
        }
    }

    fun getDetails(id: String) {

        viewModelScope.launch(Dispatchers.IO) {

            if (id.isNotBlank() && _mealDetails.value is UiState.Loading) {


                val result = async {
                    repository.getMealsDetails(id)
                }.await()

                withContext(Dispatchers.Main) {
                    when (result) {
                        is ResultState.Error -> {
                            _mealDetails.postValue(UiState.Error(result.errorMessage))
                        }

                        is ResultState.Success -> {
                            val meals = result.data?.meals
                            if (!meals.isNullOrEmpty()) {
                                Log.d("mealDetails", meals.first().toString())
                                _mealDetails.postValue(UiState.Success(meals.first()))
                            } else {
                                _mealDetails.postValue(UiState.Error("No meal details found"))
                                Log.d("mealDetails", "No meals found for id: $id or data is null")
                            }
                        }


                    }
                }
            }
        }
    }

    fun addFavoriteRecipe(mealsDetails: MealsDetails, count : Int) {
        val meal = Meals(
            mealsDetails.idMeal,
            mealsDetails.strMeal,
            mealsDetails.strMealThumb,
            mealsDetails.strCategory,
            count
        )

        if (meal != null) {

            viewModelScope.launch(Dispatchers.IO) {

                Log.i("addeddddd", "yes 2")
                meal.count += 1
                favoriteRecipeRepo.addFavouriteRecipe(meal)
            }

        }
    }

    fun isFavorite(id: String) {
        viewModelScope.launch {
            val isFavorite = favoriteRecipeRepo.getStatusOfRecipe(id)
            _isFavorite.postValue(isFavorite)
        }

    }

    fun deleteFavoriteRecipe(mealsDetails: MealsDetails, count : Int) {
        val meal = Meals(
            mealsDetails.idMeal,
            mealsDetails.strMeal,
            mealsDetails.strMealThumb,
            mealsDetails.strCategory,
            count
        )
        if (meal != null) {

            viewModelScope.launch(Dispatchers.IO) {
                meal.count -= 1
                favoriteRecipeRepo.deleteFavouriteRecipeList(meal)
            }
        }
    }


}

class DetailsViewModelFactory(
    private val repository: MealsRepo,
    private val favoriteRecipeRepo: FavoriteRecipeRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(repository, favoriteRecipeRepo) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
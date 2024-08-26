package com.example.iti_project.data.DataSource.RemoteDataSource

import com.example.iti_project.data.models.CategoryResponse
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.MealsResponse
import com.example.iti_project.data.models.MealsResponseDetails
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.SearchCategoryResponse
import org.intellij.lang.annotations.Pattern
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getMeals(): ResultState<MealsResponse>
    suspend fun getMealsDetails(MealId:String): ResultState<MealsResponseDetails>
    suspend fun  getMealbyname(MealName:String): ResultState<MealsResponse>

    suspend fun  getCategories(): ResultState<CategoryResponse>

    suspend fun  getCategoryByName(categoryName:String): ResultState<SearchCategoryResponse>


}
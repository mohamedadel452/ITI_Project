package com.example.iti_project.data.DataSource.RemoteDataSource

import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.MealsResponse
import com.example.iti_project.data.models.ResultState
import org.intellij.lang.annotations.Pattern

interface RemoteDataSource {
    suspend fun getMeals(): ResultState<MealsResponse>
    suspend fun getMealsDetails(MealId:String): Meals
    suspend fun  getMealbyname(MealName:String): ResultState<MealsResponse>
}
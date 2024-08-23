package com.example.iti_project.data.repo.Meals

import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.MealsResponse
import com.example.iti_project.data.models.MealsResponseDetails
import com.example.iti_project.data.models.ResultState
import org.intellij.lang.annotations.Pattern
import retrofit2.Response

interface MealsRepo {
    suspend fun getMeals():ResultState<MealsResponse>
    suspend fun getMealsDetails(MealId:String): ResultState<MealsResponseDetails>
    suspend fun  getMealbyname(MealName:String): ResultState<MealsResponse>
}
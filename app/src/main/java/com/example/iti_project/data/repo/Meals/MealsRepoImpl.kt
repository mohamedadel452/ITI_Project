package com.example.iti_project.data.repo.Meals

import com.example.iti_project.data.DataSource.RemoteDataSource.RemoteDataSource
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.MealsResponse
import com.example.iti_project.data.models.ResultState
import org.intellij.lang.annotations.Pattern

class MealsRepoImpl (
    private val remoteDataSource: RemoteDataSource

) : MealsRepo {
    override suspend fun getMeals(pattern: String): ResultState<MealsResponse> {
        return  remoteDataSource.getMeals(pattern)
    }

    override suspend fun getMealsDetails(MealId: String): Meals {
        return  remoteDataSource.getMealsDetails(MealId)
    }

    override suspend fun getMealbyname(MealName: String): ResultState<MealsResponse> {
        return  remoteDataSource.getMealbyname(MealName)
    }
}



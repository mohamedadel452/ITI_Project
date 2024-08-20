package com.example.iti_project.data.repo.Meals

import com.example.iti_project.data.DataSource.RemoteDataSource.RemoteDataSource
import com.example.iti_project.data.DataSource.RemoteDataSource.RetrofitClient
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.MealsResponse
import com.example.iti_project.data.models.ResultState
import org.intellij.lang.annotations.Pattern

class MealsRepoImpl (
    private val remoteDataSource: RemoteDataSource = RetrofitClient

) : MealsRepo {
    override suspend fun getMeals(): ResultState<MealsResponse> {
        return  remoteDataSource.getMeals()
    }

    override suspend fun getMealsDetails(MealId: String): Meals {
        return  remoteDataSource.getMealsDetails(MealId)
    }

    override suspend fun getMealbyname(MealName: String): ResultState<MealsResponse> {
        return  remoteDataSource.getMealbyname(MealName)
    }
}



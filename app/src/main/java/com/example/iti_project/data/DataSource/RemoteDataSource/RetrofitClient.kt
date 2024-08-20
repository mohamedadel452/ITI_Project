package com.example.iti_project.data.DataSource.RemoteDataSource

import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.MealsResponse
import com.example.iti_project.data.models.ResultState
import com.google.gson.GsonBuilder
import org.intellij.lang.annotations.Pattern
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient : RemoteDataSource {
    private val gson
        get() = GsonBuilder().serializeNulls().create()

    private val retrofit
        get() = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api.php/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override suspend fun getMeals(pattern: String): ResultState<MealsResponse> {
        return try {
            val response = apiService.getMeals(pattern)
            if (response.isSuccessful) {
                response.body()?.let {
                    ResultState.Success(it)
                } ?: ResultState.Error(Exception("Empty response body").toString())
            } else {
                ResultState.Error(Exception("Network call failed: ${response.code()} ${response.message()}").toString())
            }
        } catch (e: Exception) {
            ResultState.Error(e.toString())
        }
    }


    override suspend fun getMealsDetails(MealId: String): Meals {
       return apiService.getMealsDetails(MealId)
    }

    override suspend fun getMealbyname(MealName: String): ResultState<MealsResponse> {
        return try {
            val response = apiService.getMealbyname(MealName)
            if (response.isSuccessful) {
                response.body()?.let {
                    ResultState.Success(it)
                } ?: ResultState.Error(Exception("Empty response body").toString())
            } else {
                ResultState.Error(Exception("Network call failed: ${response.code()} ${response.message()}").toString())
            }
        } catch (e: Exception) {
            ResultState.Error(e.toString())
        }
    }

}
package com.example.iti_project.data.DataSource.RemoteDataSource

import android.util.Log
import com.example.iti_project.data.models.CategoryResponse
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.models.MealsResponse
import com.example.iti_project.data.models.MealsResponseDetails
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.SearchCategoryResponse
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient : RemoteDataSource {
    private val gson
        get() =  GsonBuilder().setLenient().serializeNulls().create()

    private val retrofit
        get() = Retrofit.Builder()
            .baseUrl("https://themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override suspend fun getMeals(): ResultState<MealsResponse> {
        return try {
            val response = apiService.getMeals()
            if (response.isSuccessful) {
              Log.e( "getMealsCode:",response.code().toString())
                response.body()?.let {
                    ResultState.Success(it)
                } ?: ResultState.Error(Exception("Empty response body").toString())
            } else {
                Log.e( "getMealsCode:","Network call failed:")
                ResultState.Error(Exception("Network call failed: ${response.code()} ${response.message()}").toString())
            }
        } catch (e: Exception) {
            ResultState.Error(e.toString())


        }
    }


    override suspend fun getMealsDetails(MealId: String): ResultState<MealsResponseDetails> {
        return try {
            Log.e( "MealId:",MealId.toString())
            val response = apiService.getMealsDetails(MealId)

            if (response.isSuccessful) {
                Log.e( "getMealsByIdBodey:",response.body().toString())
                Log.e( "getMealsByIdCode:",response.code().toString())
                response.body()?.let {
                    ResultState.Success(it)
                }?: ResultState.Error(Exception("Empty response body").toString())
            }
            else {
                ResultState.Error(Exception("Network call failed: ${response.code()} ${response.message()}").toString())
            }}
        catch (e: Exception) {
            ResultState.Error(e.toString())
        }
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

    override suspend fun getCategories(): ResultState<CategoryResponse> {
        return try {
            val response = apiService.getCategories()
            if (response.isSuccessful) {
                Log.e( "getCategoriesCode:",response.code().toString())
                response.body()?.let {
                    ResultState.Success(it)
                } ?: ResultState.Error(Exception("Empty response body").toString())
            } else {
                ResultState.Error(Exception("Network call failed: ${response.code()} ${response.message()}").toString())
            }
        } catch (e: Exception) {
            Log.e( "getCategoriesCode:",(e.toString()))
            ResultState.Error(e.toString())


        }
    }

    override suspend fun getCategoryByName(categoryName: String): ResultState<SearchCategoryResponse> {
        return try {
            val response = apiService.getCategoryByName(categoryName)
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
package com.example.iti_project.data.DataSource.RemoteDataSource

import com.example.iti_project.data.models.MealsResponse
import com.example.iti_project.data.models.MealsResponseDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.php")

    suspend fun getMeals(@Query("s") pattern: String = "a"):Response<MealsResponse>

    @GET("lookup.php")   //use in details fragment
    suspend fun getMealsDetails(@Query("i") mealId: String): Response<MealsResponseDetails>

    //search by name
    @GET("search.php")
    suspend fun getMealbyname(@Query("s") mealName: String): Response<MealsResponse>
    //return list of meals

}
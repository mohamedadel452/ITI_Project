package com.example.iti_project.data.models
import com.google.gson.annotations.SerializedName


data class MealsResponse(

val  meals : List<Meals> = listOf(),
)
data class Meals (

    @SerializedName("idMeal") val idMeal: String, //id
    @SerializedName("strMeal") val strMeal: String, //name
    @SerializedName("strMealThumb") val strMealThumb: String, //thumbnail
    @SerializedName("strCategory") val strCategory : String,

    )



package com.example.iti_project.data.models
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class MealsResponse(

val  meals : List<Meals> = listOf(),
)

@Entity(tableName = "Meals")
data class Meals (

    @SerializedName("idMeal") @PrimaryKey val idMeal: String, //id
    @SerializedName("strMeal") val strMeal: String, //name
    @SerializedName("strMealThumb") val strMealThumb: String, //thumbnail
    @SerializedName("ingredient") val ingredient: Ingredient? = null,
    @SerializedName("strCategory") val strCategory : String,
    @SerializedName("strArea") val strArea : String,  //country
    @SerializedName("strInstructions") val strInstructions: String,
    @SerializedName("strYoutube") val strYoutube: String,  //youtube link
    @SerializedName("strTags") val strTags: String,
    @SerializedName("measurements") val measurements: List<String?> = listOf()
    )


data class Ingredient(
    @SerializedName("idIngredient") val idIngredient: String?,
    @SerializedName("strIngredient") val strIngredient: String?,
    @SerializedName("strDescription") val strDescription: String?,
    @SerializedName("strType") val strType: String?
)
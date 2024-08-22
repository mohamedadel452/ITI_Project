package com.example.iti_project.data.models
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class MealsResponse(

    var  meals : List<Meals> = listOf(),
)

@Entity(tableName = "Meals")
data class Meals (

    @SerializedName("idMeal") @PrimaryKey var idMeal: String, //id
    @SerializedName("strMeal") var strMeal: String, //name
    @SerializedName("strMealThumb") var strMealThumb: String, //thumbnail
    @SerializedName("ingredient") var ingredient: Ingredient? = null,
    @SerializedName("strCategory") var strCategory : String? = null,
    @SerializedName("strArea") var strArea : String? = null,  //country
    @SerializedName("strInstructions") var strInstructions: String? = null,
    @SerializedName("strYoutube") var strYoutube: String? = null,  //youtube link
    @SerializedName("strTags") var strTags: String? = null,
    @SerializedName("measurements") var measurements: List<String?>? = emptyList()
)


data class Ingredient(
    @SerializedName("idIngredient") var idIngredient: String? = null ,
    @SerializedName("strIngredient") var strIngredient: String?= null ,
    @SerializedName("strDescription") var strDescription: String?= null ,
    @SerializedName("strType") var strType: String?= null
)
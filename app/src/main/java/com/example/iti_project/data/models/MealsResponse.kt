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


data class MealsResponseDetails(

    var  meals : List<MealsDetails> = listOf(),
)

data class MealsDetails (

    @SerializedName("idMeal") @PrimaryKey var idMeal: String, //id
    @SerializedName("strMeal") var strMeal: String, //name
    @SerializedName("strMealThumb") var strMealThumb: String, //thumbnail
    @SerializedName("strCategory") var strCategory : String? = null,
    @SerializedName("strArea") var strArea : String? = null,  //country
    @SerializedName("strInstructions") var strInstructions: String? = null,
    @SerializedName("strYoutube") var strYoutube: String? = null,  //youtube link
    @SerializedName("strTags") var strTags: String? = null,
    @SerializedName("measurements") var measurements: List<String?>? = emptyList(),
    val strIngredient1: String? = null,
    val strIngredient2: String? = null,
    val strIngredient3: String? = null,
    val strIngredient4: String? = null,
    val strIngredient5: String? = null,
    val strIngredient6: String? = null,
    val strIngredient7: String? = null,
    val strIngredient8: String? = null,
    val strIngredient9: String? = null,
    val strIngredient10: String? = null,
    val strIngredient11: String? = null,
    val strIngredient12: String? = null,
    val strIngredient13: String? = null,
    val strIngredient14: String? = null,
    val strIngredient15: String? = null,
    val strIngredient16: String? = null,
    val strIngredient17: String? = null,
    val strIngredient18: String? = null,
    val strIngredient19: String? = null,
    val strIngredient20: String? = null,
    val strMeasure1: String? = null,
    val strMeasure2: String? = null,
    val strMeasure3: String? = null,
    val strMeasure4: String? = null,
    val strMeasure5: String? = null,
    val strMeasure6: String? = null,
    val strMeasure7: String? = null,
    val strMeasure8: String? = null,
    val strMeasure9: String? = null,
    val strMeasure10: String? = null,
    val strMeasure11: String? = null,
    val strMeasure12: String? = null,
    val strMeasure13: String? = null,
    val strMeasure14: String? = null,
    val strMeasure15: String? = null,
    val strMeasure16: String? = null,
    val strMeasure17: String? = null,
    val strMeasure18: String? = null,
    val strMeasure19: String? = null,
    val strMeasure20: String? = null






)



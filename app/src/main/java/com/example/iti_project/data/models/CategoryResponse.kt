package com.example.iti_project.data.models

data class SearchCategoryResponse(

    val meals: List<CategoryMealResponse>
)

data class CategoryMealResponse(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

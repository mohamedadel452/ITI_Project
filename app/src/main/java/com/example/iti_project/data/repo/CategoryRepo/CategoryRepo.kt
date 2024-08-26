package com.example.iti_project.data.repo.CategoryRepo

import com.example.iti_project.data.models.CategoryResponse
import com.example.iti_project.data.models.MealsResponse
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.SearchCategoryResponse

interface CategoryRepo {

    suspend fun getCategories(): ResultState<CategoryResponse>
    suspend fun  getCategoryByName(categoryName:String): ResultState<SearchCategoryResponse>
}
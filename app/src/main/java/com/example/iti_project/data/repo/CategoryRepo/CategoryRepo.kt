package com.example.iti_project.data.repo.CategoryRepo

import com.example.iti_project.data.models.CategoryResponse
import com.example.iti_project.data.models.ResultState

interface CategoryRepo {

    suspend fun getCategories(): ResultState<CategoryResponse>
}
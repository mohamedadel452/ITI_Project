package com.example.iti_project.data.repo.CategoryRepo

import android.util.Log
import com.example.iti_project.data.DataSource.RemoteDataSource.RemoteDataSource
import com.example.iti_project.data.DataSource.RemoteDataSource.RetrofitClient
import com.example.iti_project.data.models.CategoryResponse
import com.example.iti_project.data.models.ResultState
import com.example.iti_project.data.models.SearchCategoryResponse

class CategoryRepoImp (
    private val remoteDataSource: RemoteDataSource = RetrofitClient
): CategoryRepo {
    override suspend fun getCategories(): ResultState<CategoryResponse> {
        Log.e( "getCategoriesCode:","repo")
        return remoteDataSource.getCategories()
    }

    override suspend fun getCategoryByName(categoryName: String): ResultState<SearchCategoryResponse> {
      return  remoteDataSource.getCategoryByName(categoryName)
    }
}

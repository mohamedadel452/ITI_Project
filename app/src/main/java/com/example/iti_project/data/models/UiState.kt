package com.example.iti_project.data.models

// Between ViewModel and View(fragment activity)
sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val errorMessage: String) : UiState<Nothing>()
}

//Between Data Source until view Model
sealed class ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error(val errorMessage: String) : ResultState<Nothing>()
}
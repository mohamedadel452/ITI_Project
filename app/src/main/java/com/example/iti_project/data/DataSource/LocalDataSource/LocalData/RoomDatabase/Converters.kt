package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase

import androidx.room.TypeConverter
import com.example.iti_project.data.models.Ingredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromListOfStringsToString(list: List<String>): String {
        return Gson().toJson(list)
    }

    // Convert String (JSON) to List<String?>
    @TypeConverter
    fun fromStringToListOfStrings(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }

}
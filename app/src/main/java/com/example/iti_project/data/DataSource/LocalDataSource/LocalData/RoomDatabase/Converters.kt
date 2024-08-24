package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase

import androidx.room.TypeConverter
import com.example.iti_project.data.models.Ingredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromMutableListOfStringsToString(list: MutableList<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToMutableListOfStrings(value: String): MutableList<String> {
        val type = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(value, type)
    }
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

    @TypeConverter
    fun fromIngredientToString(list:Ingredient): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToIngredient(value: String): Ingredient{

        return if (value.isNullOrEmpty() ) {
            Ingredient(null,null,null,null)
        }else {
            try {
            val type = object : TypeToken<Ingredient>() {}.type
            Gson().fromJson(value, type)
            } catch (e: Exception) {
                // If parsing fails, handle the case (e.g., log and return empty list or a list with a single string)
                e.printStackTrace()
                Ingredient(null,null,null,null)
            }
        }
    }


}
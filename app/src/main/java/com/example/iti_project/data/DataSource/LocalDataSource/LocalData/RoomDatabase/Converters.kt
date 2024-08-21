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
    fun fromListOfStringsToString(list: List<String?>?): String? {
        return if (list == null) {
            "null"
        } else {
            Gson().toJson(list)
        }
    }

    @TypeConverter
    fun fromStringToListOfStrings(value: String?): List<String?>? {
        return if (value == null) {
            listOf()
        } else {
            val type = object : TypeToken<List<String?>>() {}.type
            Gson().fromJson(value, type)
        }
    }

//    @TypeConverter
//    fun fromListOfStringsOrNullToString(list: List<String>): String {
//        return Gson().toJson(list)
//    }
//
//
//    @TypeConverter
//    fun fromStringToListOfStringsOrNull(value: String): List<String> {
//        val type = object : TypeToken<List<String>>() {}.type
//        return Gson().fromJson(value, type)
//    }

    @TypeConverter
    fun fromIngredientOfStringsToString(list:Ingredient): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToIngredientOfStrings(value: String): Ingredient{
        val type = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(value, type)
    }


}
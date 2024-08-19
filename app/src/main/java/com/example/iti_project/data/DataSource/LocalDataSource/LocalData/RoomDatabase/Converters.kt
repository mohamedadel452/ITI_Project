package com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromListOfStringsToString(list: MutableList<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToListOfStrings(value: String): MutableList<String> {
        val type = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(value, type)
    }





}
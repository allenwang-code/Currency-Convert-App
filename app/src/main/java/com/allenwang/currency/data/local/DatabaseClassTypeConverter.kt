package com.allenwang.currency.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import javax.inject.Inject

// @ProvidedTypeConverter
class DatabaseClassTypeConverter {

    @Inject lateinit var moshi: Moshi

    @TypeConverter
    fun doubleMapToString(map: Map<String, Double>): String {
        return moshi.adapter(Map::class.java).toJson(map)
    }

    @TypeConverter
    fun stringToDoubleMap(value: String): Map<String, Double> =
        moshi.adapter(Map::class.java).fromJson(value) as Map<String, Double>


    @TypeConverter
    fun stringMapToString(map: Map<String, String>): String {
        return moshi.adapter(Map::class.java).toJson(map)
    }

    @TypeConverter
    fun stringToStringMap(value: String): Map<String, String> =
        moshi.adapter(Map::class.java).fromJson(value) as Map<String, String>
}
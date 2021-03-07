package com.allenwang.currency.data.unity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.allenwang.currency.data.local.CClassTypeConverter
import com.squareup.moshi.Json

@TypeConverters(CClassTypeConverter::class)
@Entity(tableName = "supported_currencies")
data class SupportedCurrency(
    @Json(name = "success") val success: Boolean,
    @Json(name = "terms") val terms: String,
    @Json(name = "privacy") val privacy: String,
    @Json(name = "quotes") val quotes: Map<String, String>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0
}
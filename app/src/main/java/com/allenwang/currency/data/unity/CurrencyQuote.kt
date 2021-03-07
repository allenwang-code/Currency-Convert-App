package com.allenwang.currency.data.unity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.allenwang.currency.data.local.CClassTypeConverter
import com.squareup.moshi.Json

@TypeConverters(CClassTypeConverter::class)
@Entity(tableName = "currency_quotes")
data class CurrencyQuote(
    @Json(name = "success") val success: Boolean,
    @Json(name = "terms") val terms: String,
    @Json(name = "privacy") val privacy: String,
    @Json(name = "historical") val historical: Boolean,
    @PrimaryKey @Json(name = "date") val date: String,
    @Json(name = "timestamp") val timestamp: Int,
    @Json(name = "source") val source: String,
    @Json(name = "quotes") val quotes: Map<String, Double>
)
package com.allenwang.currency.data.unity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.allenwang.currency.data.local.CClassTypeConverter
import com.squareup.moshi.Json

@TypeConverters(CClassTypeConverter::class)
@Entity(tableName = "currency_quotes")
data class CurrencyQuote(
    @PrimaryKey @Json(name = "currencyCode") val currencyCode: String,
    @Json(name = "quote") val quote: Double
)
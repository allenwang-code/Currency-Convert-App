package com.allenwang.currency.data.unity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.allenwang.currency.data.local.DatabaseClassTypeConverter
import com.squareup.moshi.Json

@TypeConverters(DatabaseClassTypeConverter::class)
@Entity(tableName = "currency_quotes")
data class CurrencyQuote(
    @PrimaryKey @Json(name = "currencyCode") val currencyCode: String,
    @Json(name = "quote") val quote: Double
)
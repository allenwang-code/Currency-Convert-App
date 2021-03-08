package com.allenwang.currency.data.unity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.allenwang.currency.data.local.CClassTypeConverter
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@TypeConverters(CClassTypeConverter::class)
@Entity(tableName = "supported_currencies")
@Serializable
data class SupportedCurrency(
    @PrimaryKey @Json(name = "currency_key") val currencyKey: String,
    @Json(name = "currency_value") val currencyValue: String,
) : java.io.Serializable
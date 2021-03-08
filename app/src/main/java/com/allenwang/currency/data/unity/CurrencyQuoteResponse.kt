package com.allenwang.currency.data.unity

data class CurrencyQuoteResponse(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val historical: Boolean,
    val date: String,
    val timestamp: Int,
    val source: String,
    val quotes: Map<String, Double>
)
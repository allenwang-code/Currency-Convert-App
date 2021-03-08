package com.allenwang.currency.data.unity

data class SupportedCurrencyResponse(
   val success: Boolean,
   val terms: String,
   val privacy: String,
   val currencies: Map<String, String>
)
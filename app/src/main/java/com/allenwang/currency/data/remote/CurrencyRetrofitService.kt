package com.allenwang.currency.data.remote

import com.allenwang.currency.data.unity.CurrencyQuote
import com.allenwang.currency.data.unity.CurrencyQuoteResponse
import com.allenwang.currency.data.unity.SupportedCurrency
import com.allenwang.currency.data.unity.SupportedCurrencyResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRetrofitService {

    @GET("list")
    fun getSupportedCurrencies(@Query("access_key") accessKey: String): Observable<SupportedCurrencyResponse>

    @GET("historical")
    fun getCurrencyQuote(
        @Query("access_key") accessKey: String,
        @Query("date") date: String,
        //@Query("source") source: String,
        @Query("currencies") currencies: String
    ): Observable<CurrencyQuoteResponse>
}


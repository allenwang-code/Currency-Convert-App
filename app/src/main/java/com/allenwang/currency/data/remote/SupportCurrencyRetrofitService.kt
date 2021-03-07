package com.allenwang.currency.data.remote

import com.allenwang.currency.data.unity.CurrencyQuote
import com.allenwang.currency.data.unity.SupportedCurrency
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SupportCurrencyRetrofitService {

    @GET("list")
    fun getSupportedCurrencies(@Query("access_key") accessKey: String): Single<SupportedCurrency>

    @GET("historical")
    fun getCurrencyQuote(@Query("access_key") accessKey: String): Single<CurrencyQuote>
}

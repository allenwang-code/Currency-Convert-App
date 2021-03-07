package com.allenwang.currency.data.remote

import com.allenwang.currency.BuildConfig
import com.allenwang.currency.data.unity.SupportedCurrency
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SupportCurrencyRemoteDataSource @Inject constructor(
    private val service: SupportCurrencyRetrofitService
) {
    fun getSupportedCurrencies(): Single<SupportedCurrency> {
       return service.getSupportedCurrencies(BuildConfig.CURRENCY_LAYER_KEY)
    }
}

package com.allenwang.currency.data.remote

import com.allenwang.currency.BuildConfig
import com.allenwang.currency.data.unity.CurrencyQuote
import io.reactivex.rxjava3.core.Observable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class CurrencyQuoteApi @Inject constructor(
    private val service: CurrencyRetrofitService
) {
    fun getCurrencyQuote(source: String): Observable<List<CurrencyQuote>> {
        return service.getCurrencyQuote(
            BuildConfig.CURRENCY_LAYER_KEY,
            getTimeYesterday(),
            //source,
            "USD,BTC,JPY,CNY,EUR"
        ).map {
                val list = ArrayList<CurrencyQuote>()
                for ((key, value) in it.quotes) {
                    val c = CurrencyQuote(key, value)
                    list.add(c)
                }
                list
            }.flatMap {
                Observable.just(it)
            }
    }

    private fun getTimeYesterday(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val formatter = SimpleDateFormat("YYYY-MM-dd")
        return formatter.format(calendar.time)
    }
}

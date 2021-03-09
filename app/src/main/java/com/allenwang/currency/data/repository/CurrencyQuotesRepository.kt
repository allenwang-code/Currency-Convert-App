package com.allenwang.currency.data.repository

import com.allenwang.currency.data.local.CurrencyQuoteDao
import com.allenwang.currency.data.remote.CurrencyQuoteApi
import com.allenwang.currency.data.unity.CurrencyQuote
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyQuotesRepository @Inject constructor(
    private val currencyQuoteDao: CurrencyQuoteDao,
    private val currencyQuoteApi: CurrencyQuoteApi
) {

    fun getCurrencyQuotesFromDb(): Observable<List<CurrencyQuote>> {
        return currencyQuoteDao.getAllCurrencyQuotes()
            .doOnNext {
                // Log
            }
    }

    fun getCurrencyQuotesFromApi(source: String): Observable<List<CurrencyQuote>> {
        return currencyQuoteApi.getCurrencyQuote(source)
            .doOnNext {
                // Log
                storeCurrencyQuotesInDb(it)
            }
    }

    private fun storeCurrencyQuotesInDb(currencies: List<CurrencyQuote>) {
        Observable.fromCallable { currencyQuoteDao.insertAll(currencies) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                // Log
            }
    }


}
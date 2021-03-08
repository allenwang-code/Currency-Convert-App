package com.allenwang.currency.data.repository

import com.allenwang.currency.data.local.SupportedCurrencyDao
import com.allenwang.currency.data.remote.SupportedCurrencyApi
import com.allenwang.currency.data.unity.SupportedCurrency
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupportedCurrencyRepository @Inject constructor(
    private val supportedCurrencyDao: SupportedCurrencyDao,
    private val supportedCurrencyApi: SupportedCurrencyApi
) {

    fun getSupportedCurrencies(): Observable<List<SupportedCurrency>> {
        return Observable.concatArrayEager(
            getSupportedCurrenciesFromDb(),
            getSupportedCurrenciesFromApi())
    }

    private fun getSupportedCurrenciesFromDb(): Observable<List<SupportedCurrency>> {
        return supportedCurrencyDao.getAllSupportedCurrencies()
            .filter { it.isNotEmpty() }
            .doOnNext {
                // Log
            }
    }

    private fun getSupportedCurrenciesFromApi(): Observable<List<SupportedCurrency>> {
        return supportedCurrencyApi.getSupportedCurrencies()
            .doOnNext {
                // Log
                storeSupportedCurrenciesInDb(it)
            }
    }

    private fun storeSupportedCurrenciesInDb(currencies: List<SupportedCurrency>) {
        Observable.fromCallable { supportedCurrencyDao.insertAll(currencies) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                // Log
            }
    }


}
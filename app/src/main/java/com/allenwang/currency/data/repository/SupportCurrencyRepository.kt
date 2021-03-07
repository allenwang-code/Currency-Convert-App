package com.allenwang.currency.data.repository

import com.allenwang.currency.data.local.SupportCurrencyDao
import com.allenwang.currency.data.remote.SupportCurrencyRemoteDataSource
import com.allenwang.currency.data.unity.SupportedCurrency
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupportCurrencyRepository @Inject constructor(
    private val supportCurrencyDao: SupportCurrencyDao,
    private val supportCurrencyRemoteDataSource: SupportCurrencyRemoteDataSource
) {

//    fun getSupportedCyrrencies(): Observable<List<SupportedCurrency>> {
//        return Observable.concatArray(
//            supportCurrencyDao.getAllSupportedCurrencies(),
//            supportCurrencyRemoteDataSource.getSupportedCurrencies())
//    }


}
package com.allenwang.currency.ui.quotes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allenwang.currency.data.repository.CurrencyQuotesRepository
import com.allenwang.currency.data.unity.CurrencyQuote
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyQuotesViewModel
@Inject constructor(private val currencyQuotesRepository: CurrencyQuotesRepository) : ViewModel() {

    val quotes: MutableLiveData<List<CurrencyQuote>> by lazy {
        MutableLiveData<List<CurrencyQuote>>()
    }

    val error: MutableLiveData<Throwable> by lazy {
        MutableLiveData<Throwable>()
    }

    var compositeDisposable = CompositeDisposable()

    fun getCurrencyQuotes(sourceCode: String) {
        compositeDisposable.add(
            currencyQuotesRepository.getCurrencyQuotesFromDb()
                .subscribeOn(Schedulers.io())
                .switchMap { dbList ->
                    if (dbList.isEmpty()) currencyQuotesRepository.getCurrencyQuotesFromApi(sourceCode)
                     else Observable.just(dbList)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dbList ->
                    quotes.value = dbList
                }, {
                    error.value = it
                })
        )
    }

    fun updateCurrencyQuotes(sourceCode: String) {
        compositeDisposable.add(
            Observable.interval(30, TimeUnit.MINUTES)
                .flatMap {
                    currencyQuotesRepository.getCurrencyQuotesFromApi(sourceCode)
                }
                .subscribe({
                    Log.d("CurrencyQuotesViewModel", "update: $it from api")
                }, {
                    error.value = it
                })
        )
    }
}
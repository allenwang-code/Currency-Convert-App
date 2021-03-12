package com.allenwang.currency.ui.quotes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allenwang.currency.data.repository.CurrencyQuotesRepository
import com.allenwang.currency.data.unity.CurrencyQuote
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyQuotesViewModel
@Inject constructor(private val currencyQuotesRepository: CurrencyQuotesRepository) : ViewModel() {

    val quotes: MutableLiveData<List<CurrencyQuote>> by lazy {
        MutableLiveData<List<CurrencyQuote>>()
    }
    val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val error: MutableLiveData<Throwable> by lazy {
        MutableLiveData<Throwable>()
    }

    var updateDisposable: Disposable? = null
    private var compositeDisposable = CompositeDisposable()

    fun getCurrencyQuotes(sourceCode: String) {
        loading.value = true
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
                    loading.value = false
                }, {
                    error.value = it
                    loading.value = false
                })
        )
    }

    fun updateCurrencyQuotes(sourceCode: String, secondsToUpdate: Long) {
        loading.value = true
        updateDisposable =
            Observable.interval(secondsToUpdate, TimeUnit.SECONDS)
                .flatMap {
                    currencyQuotesRepository.getCurrencyQuotesFromApi(sourceCode)
                }
                .subscribe {
                    Log.d("CurrencyQuotesViewModel", "update: $it from api")
                }

    }

    override fun onCleared() {
        super.onCleared()
        updateDisposable?.dispose()
        compositeDisposable.clear()
    }
}
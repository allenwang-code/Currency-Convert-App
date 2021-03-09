package com.allenwang.currency.ui.supported_currency

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allenwang.currency.data.repository.SupportedCurrencyRepository
import com.allenwang.currency.data.unity.CurrencyQuote
import com.allenwang.currency.data.unity.SupportedCurrency
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SupportedCurrenciesViewModel
@Inject constructor(private val supportedCurrencyRepository: SupportedCurrencyRepository) : ViewModel() {

    val supportedCurrency: MutableLiveData<List<SupportedCurrency>> by lazy {
        MutableLiveData<List<SupportedCurrency>>()
    }

    val error: MutableLiveData<Throwable> by lazy {
        MutableLiveData<Throwable>()
    }

    private var compositeDisposable = CompositeDisposable()

    fun getCurrencies() {
        compositeDisposable.add(
            supportedCurrencyRepository.getSupportedCurrencies()
                .debounce(400, TimeUnit.MILLISECONDS)
                .onErrorReturn {
                    emptyList()
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    supportedCurrency.value = it
                }, {
                    error.value = it
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
package com.allenwang.currency.ui.supported_currency

import androidx.lifecycle.ViewModel
import com.allenwang.currency.data.repository.SupportedCurrencyRepository
import com.allenwang.currency.data.unity.SupportedCurrency
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observable.just
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SupportedCurrenciesViewModel
@Inject constructor(private val supportedCurrencyRepository: SupportedCurrencyRepository) : ViewModel() {


    fun getCurrencies(): Observable<List<SupportedCurrency>> {
        return supportedCurrencyRepository.getSupportedCurrencies()
            .debounce(400, TimeUnit.MILLISECONDS)
            .onErrorReturn {
                emptyList()
            }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
package com.allenwang.currency.ui.convert

import androidx.lifecycle.ViewModel
import com.allenwang.currency.data.repository.CurrencyQuotesRepository
import com.allenwang.currency.data.repository.SupportedCurrencyRepository
import com.allenwang.currency.data.unity.CurrencyQuote
import com.allenwang.currency.data.unity.SupportedCurrency
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observable.just
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyQuotesViewModel
@Inject constructor(private val currencyQuotesRepository: CurrencyQuotesRepository) : ViewModel() {
    
    fun getCurrencyQuotes(sourceCode: String): Observable<List<CurrencyQuote>> {
        return currencyQuotesRepository.getSupportedCurrencies(sourceCode)
            .debounce(400, TimeUnit.MILLISECONDS)
            .onErrorReturn {
                emptyList()
            }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
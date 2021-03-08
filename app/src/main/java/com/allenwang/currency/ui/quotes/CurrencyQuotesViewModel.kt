package com.allenwang.currency.ui.quotes

import androidx.lifecycle.ViewModel
import com.allenwang.currency.data.repository.CurrencyQuotesRepository
import com.allenwang.currency.data.unity.CurrencyQuote
import io.reactivex.rxjava3.core.Observable
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
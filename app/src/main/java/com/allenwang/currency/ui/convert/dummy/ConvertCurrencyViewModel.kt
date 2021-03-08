package com.allenwang.currency.ui.convert.dummy

import com.allenwang.currency.data.repository.SupportedCurrencyRepository
import javax.inject.Inject

class ConvertCurrencyViewModel @Inject constructor(
    private val repository: SupportedCurrencyRepository

) {
}
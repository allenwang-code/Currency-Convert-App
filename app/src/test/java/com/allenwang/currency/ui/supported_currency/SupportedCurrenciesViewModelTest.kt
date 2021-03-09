package com.allenwang.currency.ui.supported_currency

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.allenwang.currency.data.repository.CurrencyQuotesRepository
import com.allenwang.currency.data.repository.SupportedCurrencyRepository
import com.allenwang.currency.ui.quotes.CurrencyQuotesViewModel
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SupportedCurrenciesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: SupportedCurrencyRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getSupportedCurrency() {
        val viewModel = SupportedCurrenciesViewModel(repository)
        MatcherAssert.assertThat(viewModel.supportedCurrency, CoreMatchers.not(CoreMatchers.nullValue()))
    }

    @Test
    fun getError() {
        val viewModel = SupportedCurrenciesViewModel(repository)
        MatcherAssert.assertThat(viewModel.error, CoreMatchers.not(CoreMatchers.nullValue()))
    }
}
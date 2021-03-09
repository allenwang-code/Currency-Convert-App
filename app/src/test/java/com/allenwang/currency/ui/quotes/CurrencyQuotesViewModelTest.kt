package com.allenwang.currency.ui.quotes

import androidx.lifecycle.MutableLiveData
import com.allenwang.currency.RxImmediateSchedulerRule
import com.allenwang.currency.data.repository.CurrencyQuotesRepository
import com.allenwang.currency.data.unity.CurrencyQuote
import com.nhaarman.mockitokotlin2.never
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.ClassRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CurrencyQuotesViewModelTest {

    @Mock
    private lateinit var repository: CurrencyQuotesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCurrencyQuotesFromAPIIfDbIsNull() {
        val emptyList = emptyList<CurrencyQuote>()
        val apiList = listOf(CurrencyQuote("OOXX", 123.123))
        Mockito.`when`(repository.getCurrencyQuotesFromDb()).thenReturn(Observable.just(emptyList))
        Mockito.`when`(repository.getCurrencyQuotesFromApi("")).thenReturn(Observable.just(apiList))

        val viewModel = CurrencyQuotesViewModel(repository)
        viewModel.getCurrencyQuotes("")
        Mockito.verify(repository).getCurrencyQuotesFromApi("")
    }

    @Test
    fun getCurrencyQuotesFromAPIIfDbIsNotNull() {
        val apiList = listOf(CurrencyQuote("OOXX", 123.123))
        Mockito.`when`(repository.getCurrencyQuotesFromDb()).thenReturn(Observable.just(apiList))
        Mockito.`when`(repository.getCurrencyQuotesFromApi("")).thenReturn(Observable.empty())

        val viewModel = CurrencyQuotesViewModel(repository)
        viewModel.getCurrencyQuotes("")
        Mockito.verify(repository, never()).getCurrencyQuotesFromApi("")
    }

    @Test
    fun updateCurrencyQuotes() {
        val viewModel = CurrencyQuotesViewModel(repository)
        viewModel.updateCurrencyQuotes("",60L)
        viewModel.updateDisposable?.dispose()
        Mockito.verify(repository).getCurrencyQuotesFromApi("")
    }

    companion object {
        @get:ClassRule
        @JvmStatic
        val testSchedulerRule = RxImmediateSchedulerRule()
    }
}
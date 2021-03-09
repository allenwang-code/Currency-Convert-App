package com.allenwang.currency.ui.quotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.allenwang.currency.RxImmediateSchedulerRule
import com.allenwang.currency.data.repository.CurrencyQuotesRepository
import com.allenwang.currency.data.unity.CurrencyQuote
import com.allenwang.currency.ui.supported_currency.SupportedCurrenciesViewModel
import com.nhaarman.mockitokotlin2.never
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit


class CurrencyQuotesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: CurrencyQuotesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getQuotesLiveData() {
        val viewModel = CurrencyQuotesViewModel(repository)
        MatcherAssert.assertThat(viewModel.quotes, CoreMatchers.not(CoreMatchers.nullValue()))
    }

    @Test
    fun getErrorLiveData() {
        val viewModel = CurrencyQuotesViewModel(repository)
        MatcherAssert.assertThat(viewModel.error, CoreMatchers.not(CoreMatchers.nullValue()))
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
        val testScheduler = TestScheduler()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }

        val viewModel = CurrencyQuotesViewModel(repository)
        viewModel.updateCurrencyQuotes("", 60L)

        testScheduler.advanceTimeBy(60, TimeUnit.SECONDS)
        Mockito.verify(repository).getCurrencyQuotesFromApi("")
    }

    companion object {
        @get:ClassRule
        @JvmStatic
        val testSchedulerRule = RxImmediateSchedulerRule()
    }
}
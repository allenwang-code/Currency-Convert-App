package com.allenwang.currency.data.repository

import com.allenwang.currency.data.local.CurrencyQuoteDao
import com.allenwang.currency.data.remote.CurrencyQuoteApi
import com.nhaarman.mockitokotlin2.any
import io.reactivex.rxjava3.core.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CurrencyQuotesRepositoryTest {

    @Mock
    lateinit var currencyQuoteDao: CurrencyQuoteDao
    @Mock
    lateinit var currencyQuoteApi: CurrencyQuoteApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCurrencyQuotesFromDb() {
        Mockito.`when`(currencyQuoteDao.getAllCurrencyQuotes()).thenReturn(Observable.empty())
        CurrencyQuotesRepository(currencyQuoteDao, currencyQuoteApi).getCurrencyQuotesFromDb()
        Mockito.verify(currencyQuoteDao).getAllCurrencyQuotes()
    }

    @Test
    fun getCurrencyQuotesFromApi() {
        Mockito.`when`( currencyQuoteApi.getCurrencyQuote("USA")).thenReturn(Observable.empty())
        CurrencyQuotesRepository(currencyQuoteDao, currencyQuoteApi).getCurrencyQuotesFromApi("USA")
        Mockito.verify(currencyQuoteApi).getCurrencyQuote("USA")
    }
}
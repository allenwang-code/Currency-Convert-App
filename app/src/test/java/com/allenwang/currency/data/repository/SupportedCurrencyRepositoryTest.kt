package com.allenwang.currency.data.repository

import com.allenwang.currency.data.local.SupportedCurrencyDao
import com.allenwang.currency.data.remote.SupportedCurrencyApi
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SupportedCurrencyRepositoryTest {

    @Mock
    lateinit var supportedCurrencyDao: SupportedCurrencyDao
    @Mock
    lateinit var supportedCurrencyApi: SupportedCurrencyApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getSupportedCurrencies() {
        Mockito.`when`(supportedCurrencyDao.getAllSupportedCurrencies()).thenReturn(Observable.empty())
        Mockito.`when`(supportedCurrencyApi.getSupportedCurrencies()).thenReturn(Observable.empty())

        SupportedCurrencyRepository(supportedCurrencyDao, supportedCurrencyApi).getSupportedCurrencies()
        Mockito.verify(supportedCurrencyDao).getAllSupportedCurrencies()
        Mockito.verify(supportedCurrencyApi).getSupportedCurrencies()
    }
}
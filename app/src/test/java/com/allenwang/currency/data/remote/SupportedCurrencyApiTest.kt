package com.allenwang.currency.data.remote

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.allenwang.currency.BuildConfig
import com.allenwang.currency.data.unity.CurrencyQuoteResponse
import com.allenwang.currency.data.unity.SupportedCurrencyResponse
import com.nhaarman.mockitokotlin2.any
import io.reactivex.rxjava3.core.Observable
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@SmallTest
@Config(sdk = [Build.VERSION_CODES.O])
class SupportedCurrencyApiTest {
    @Mock
    lateinit var service: CurrencyRetrofitService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCurrencyQuote() {
        val currencies = mapOf("USD" to "USA", "JPY" to "Japan")
        val response = SupportedCurrencyResponse(true, "", "", currencies)
        val observableRes = Observable.just(response)
        Mockito.`when`(service.getSupportedCurrencies(any())).thenReturn(observableRes)

        val observableReq = SupportedCurrencyApi(service).getSupportedCurrencies()
        Assert.assertThat(observableReq.blockingFirst()[0].currencyKey, CoreMatchers.`is`("USD"))
        Assert.assertThat(observableReq.blockingFirst()[1].currencyKey, CoreMatchers.`is`("JPY"))
        Assert.assertThat(observableReq.blockingFirst()[0].currencyValue, CoreMatchers.`is`("USA"))
        Assert.assertThat(observableReq.blockingFirst()[1].currencyValue, CoreMatchers.`is`("Japan"))
    }
}
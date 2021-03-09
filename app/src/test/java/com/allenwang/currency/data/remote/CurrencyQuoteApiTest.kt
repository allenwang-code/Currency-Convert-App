package com.allenwang.currency.data.remote

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.allenwang.currency.data.unity.CurrencyQuoteResponse
import com.nhaarman.mockitokotlin2.any
import io.reactivex.rxjava3.core.Observable
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
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
class CurrencyQuoteApiTest {
    @Mock
    lateinit var service: CurrencyRetrofitService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCurrencyQuote() {
        val quotes = mapOf("OOXX" to 123.123, "XXOO" to 456.456)
        val response = CurrencyQuoteResponse(true, "", "", true, "", 0, "", quotes)
        val observableRes = Observable.just(response)
        Mockito.`when`(service.getCurrencyQuote(any(), any(), any())).thenReturn(observableRes)

        val observableReq = CurrencyQuoteApi(service).getCurrencyQuote("")
        assertThat(observableReq.blockingFirst()[0].currencyCode, `is`("OOXX"))
        assertThat(observableReq.blockingFirst()[1].currencyCode, `is`("XXOO"))
        assertThat(observableReq.blockingFirst()[0].quote, `is`(123.123))
        assertThat(observableReq.blockingFirst()[1].quote, `is`(456.456))
    }
}
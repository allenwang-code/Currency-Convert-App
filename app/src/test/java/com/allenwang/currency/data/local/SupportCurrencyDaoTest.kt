package com.allenwang.currency.data.local

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.allenwang.currency.data.unity.SupportedCurrency
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
@Config(sdk = [Build.VERSION_CODES.O])
class SupportCurrencyDaoTest {

    private lateinit var database: AppDatabase

    // Executes each currencyValue synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertSupportedCurrencyAndGetById() {
        val currency = SupportedCurrency("USD", "America")
        database.supportedCurrencyDao().insert(currency)

        val loaded = database.supportedCurrencyDao().getSupportedCurrency(currency.currencyKey).blockingFirst()

        assertThat<SupportedCurrency>(loaded, CoreMatchers.notNullValue())
        assertThat(loaded.currencyKey, `is`(currency.currencyKey))
        assertThat(loaded.currencyValue, `is`(currency.currencyValue))
    }

    @Test
    fun insertTaskReplacesOnConflict() {
        val currency = SupportedCurrency("USD", "America")
        database.supportedCurrencyDao().insert(currency)

        val newCurrency = SupportedCurrency("USD", "USA")
        database.supportedCurrencyDao().insert(newCurrency)

        val loaded = database.supportedCurrencyDao().getSupportedCurrency(currency.currencyKey).blockingFirst()
        assertThat(loaded.currencyKey, `is`(newCurrency.currencyKey))
        assertThat(loaded.currencyValue, `is`(newCurrency.currencyValue))
    }

    @Test
    fun insertTaskAndGetTasks() {
        val currency = SupportedCurrency("USD", "America")
        database.supportedCurrencyDao().insert(currency)

        val quotes = database.supportedCurrencyDao().getAllSupportedCurrencies().blockingFirst()

        assertThat(quotes.size, CoreMatchers.`is`(1))
        assertThat(quotes[0].currencyKey, `is`(currency.currencyKey))
        assertThat(quotes[0].currencyValue, `is`(currency.currencyValue))
    }
}
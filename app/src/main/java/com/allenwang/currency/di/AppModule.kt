package com.allenwang.currency.di

import android.content.Context
import com.allenwang.currency.data.local.AppDatabase
import com.allenwang.currency.data.local.CurrencyQuoteDao
import com.allenwang.currency.data.local.SupportedCurrencyDao
import com.allenwang.currency.data.remote.CurrencyQuoteApi
import com.allenwang.currency.data.remote.SupportedCurrencyApi
import com.allenwang.currency.data.remote.CurrencyRetrofitService
import com.allenwang.currency.data.repository.CurrencyQuotesRepository
import com.allenwang.currency.data.repository.SupportedCurrencyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val applicationContext: Context) {

    @Provides
    fun provideContext() = applicationContext

    @Singleton
    @Provides
    fun provideDatabase(appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideSupportedCurrencyDao(db: AppDatabase) = db.supportedCurrencyDao()

    @Singleton
    @Provides
    fun provideSupportCurrencyRemoteDataSource(service: CurrencyRetrofitService) =
        SupportedCurrencyApi(service)

    @Singleton
    @Provides
    fun provideRepository(
        supportedCurrencyApi: SupportedCurrencyApi,
        supportedCurrencyDao: SupportedCurrencyDao
    ) = SupportedCurrencyRepository(supportedCurrencyDao, supportedCurrencyApi)

    @Singleton
    @Provides
    fun provideCurrencyQuotesDao(db: AppDatabase) = db.currencyQuoteDao()

    @Singleton
    @Provides
    fun provideCurrencyQuotesRemoteDataSource(service: CurrencyRetrofitService) =
        CurrencyQuoteApi(service)

    @Singleton
    @Provides
    fun provideCurrencyQuotesRepository(
        currencyQuoteDao: CurrencyQuoteDao,
        currencyQuoteApi: CurrencyQuoteApi
    ) = CurrencyQuotesRepository(currencyQuoteDao, currencyQuoteApi)

}
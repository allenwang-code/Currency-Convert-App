package com.allenwang.currency.di

import android.content.Context
import com.allenwang.currency.data.local.AppDatabase
import com.allenwang.currency.data.local.CClassTypeConverter
import com.allenwang.currency.data.local.CurrencyQuoteDao
import com.allenwang.currency.data.local.SupportedCurrencyDao
import com.allenwang.currency.data.remote.CurrencyQuoteApi
import com.allenwang.currency.data.remote.SupportedCurrencyRemoteDataSource
import com.allenwang.currency.data.remote.CurrencyRetrofitService
import com.allenwang.currency.data.repository.CurrencyQuotesRepository
import com.allenwang.currency.data.repository.SupportedCurrencyRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val applicationContext: Context) {

    @Provides
    fun provideContext() = applicationContext

    @Provides
    fun provideCClassTypeConverter(moshi: Moshi) = CClassTypeConverter()

    @Singleton
    @Provides
    fun provideDatabase(appContext: Context, typeConverter: CClassTypeConverter) =
        AppDatabase.getDatabase(appContext, typeConverter)

    @Singleton
    @Provides
    fun provideSupportedCurrencyDao(db: AppDatabase) = db.supportedCurrencyDao()

    @Singleton
    @Provides
    fun provideSupportCurrencyRemoteDataSource(service: CurrencyRetrofitService) =
        SupportedCurrencyRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: SupportedCurrencyRemoteDataSource,
        supportedCurrencyDao: SupportedCurrencyDao
    ) = SupportedCurrencyRepository(supportedCurrencyDao, remoteDataSource)

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
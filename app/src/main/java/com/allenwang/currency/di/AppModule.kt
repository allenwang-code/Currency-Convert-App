package com.allenwang.currency.di

import android.content.Context
import com.allenwang.currency.data.local.AppDatabase
import com.allenwang.currency.data.local.CClassTypeConverter
import com.allenwang.currency.data.local.SupportedCurrencyDao
import com.allenwang.currency.data.remote.SupportedCurrencyRemoteDataSource
import com.allenwang.currency.data.remote.SupportedCurrencyRetrofitService
import com.allenwang.currency.data.repository.SupportedCurrencyRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val applicationContext: Context) {

    @Singleton
    @Provides
    fun provideSupportCurrencyRemoteDataSource(service: SupportedCurrencyRetrofitService) =
        SupportedCurrencyRemoteDataSource(service)

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
    fun provideRepository(
        remoteDataSource: SupportedCurrencyRemoteDataSource,
        supportedCurrencyDao: SupportedCurrencyDao
    ) = SupportedCurrencyRepository(supportedCurrencyDao, remoteDataSource)
}
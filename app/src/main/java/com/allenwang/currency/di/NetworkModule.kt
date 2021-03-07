package com.allenwang.currency.di

import com.allenwang.currency.BuildConfig
import com.allenwang.currency.data.remote.SupportCurrencyRetrofitService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
class NetworkModule {

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    fun provideLoginRetrofitService(moshiConverterFactory: MoshiConverterFactory, okHttpClient: OkHttpClient): SupportCurrencyRetrofitService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.CURRENCY_LAYER_URL)
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .build()
            .create(SupportCurrencyRetrofitService::class.java)
    }
}
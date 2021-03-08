package com.allenwang.currency.di

import com.allenwang.currency.BuildConfig
import com.allenwang.currency.data.remote.SupportedCurrencyRetrofitService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
class NetworkModule {

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    fun provideLoginRetrofitService(moshiConverterFactory: MoshiConverterFactory, okHttpClient: OkHttpClient): SupportedCurrencyRetrofitService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.CURRENCY_LAYER_URL)
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(SupportedCurrencyRetrofitService::class.java)
    }
}
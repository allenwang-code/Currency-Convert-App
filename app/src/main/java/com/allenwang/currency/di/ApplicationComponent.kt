package com.allenwang.currency.di

import android.content.Context
import com.allenwang.currency.data.repository.SupportCurrencyRepository
import com.allenwang.currency.ui.convert.ConvertCurrencyFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules= [AppModule::class, NetworkModule::class])
interface ApplicationComponent {
    fun inject(fragment: ConvertCurrencyFragment)
    fun context(): Context
}
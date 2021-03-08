package com.allenwang.currency.di

import android.content.Context
import com.allenwang.currency.ui.quotes.CurrencyQuotesFragment
import com.allenwang.currency.ui.supported_currency.SupportedCurrencyFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules= [AppModule::class, NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(fragment: CurrencyQuotesFragment)
    fun inject(fragment: SupportedCurrencyFragment)
    fun context(): Context
}
package com.allenwang.currency.di

import android.content.Context
import com.allenwang.currency.ui.convert.CurrencyQuotesFragment
import com.allenwang.currency.ui.supported_currency.SupportedCurrencyFragment
import com.allenwang.currency.util.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules= [AppModule::class, NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(fragment: CurrencyQuotesFragment)
    fun inject(fragment: SupportedCurrencyFragment)
    fun context(): Context
}
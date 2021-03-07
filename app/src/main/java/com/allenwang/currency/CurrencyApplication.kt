package com.allenwang.currency

import android.app.Application
import com.allenwang.currency.di.AppModule
import com.allenwang.currency.di.ApplicationComponent
import com.allenwang.currency.di.DaggerApplicationComponent
import com.allenwang.currency.di.NetworkModule

class CurrencyApplication: Application() {
    var appComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        if (appComponent == null) {
            appComponent = DaggerApplicationComponent.builder()
                .appModule(AppModule(applicationContext))
                .networkModule(NetworkModule())
                .build()
        }
    }
}
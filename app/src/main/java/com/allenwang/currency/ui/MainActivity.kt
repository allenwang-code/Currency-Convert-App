package com.allenwang.currency.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allenwang.currency.R
import com.allenwang.currency.ui.quotes.CurrencyQuotesFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) { // initial transaction should be wrapped like this
            supportFragmentManager.beginTransaction()
                .replace(R.id.root_container, CurrencyQuotesFragment.newInstance())
                .commitAllowingStateLoss()
        }
    }
}
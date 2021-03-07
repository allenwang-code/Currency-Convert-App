package com.allenwang.currency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allenwang.currency.ui.convert.ConvertCurrencyFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) { // initial transaction should be wrapped like this
            supportFragmentManager.beginTransaction()
                .replace(R.id.root_container, ConvertCurrencyFragment.newInstance(1))
                .commitAllowingStateLoss()
        }
    }
}
package com.allenwang.currency.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.allenwang.currency.data.unity.CurrencyQuote
import com.allenwang.currency.data.unity.SupportedCurrency

@Dao
interface CurrencyQuoteDao {

    @Query("SELECT * FROM currency_quotes")
    fun getAllSupportedCurrencies() : LiveData<List<CurrencyQuote>>

    @Query("SELECT * FROM supported_currencies WHERE id = :id")
    fun getCurrency(id: Int): LiveData<CurrencyQuote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CurrencyQuote>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyQuote: CurrencyQuote)
}

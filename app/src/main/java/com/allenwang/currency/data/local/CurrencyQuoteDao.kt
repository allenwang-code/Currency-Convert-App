package com.allenwang.currency.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.allenwang.currency.data.unity.CurrencyQuote
import io.reactivex.rxjava3.core.Observable

@Dao
interface CurrencyQuoteDao {

    @Query("SELECT * FROM currency_quotes")
    fun getAllCurrencyQuotes() : Observable<List<CurrencyQuote>>

    @Query("SELECT * FROM currency_quotes WHERE currencyCode = :currencyCode")
    fun getCurrencyQuote(currencyCode: String): Observable<CurrencyQuote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CurrencyQuote>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyQuote: CurrencyQuote)

    /** Can add update/delete if needed **/
}

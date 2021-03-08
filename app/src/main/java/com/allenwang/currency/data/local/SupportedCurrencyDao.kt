package com.allenwang.currency.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.allenwang.currency.data.unity.SupportedCurrency
import io.reactivex.rxjava3.core.Observable

@Dao
interface SupportedCurrencyDao {

    @Query("SELECT * FROM supported_currencies")
    fun getAllSupportedCurrencies() : Observable<List<SupportedCurrency>>

    @Query("SELECT * FROM supported_currencies WHERE currencyKey = :currencyKey")
    fun getSupportedCurrency(currencyKey: String): Observable<SupportedCurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SupportedCurrency>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(supportedCurrency: SupportedCurrency)
}

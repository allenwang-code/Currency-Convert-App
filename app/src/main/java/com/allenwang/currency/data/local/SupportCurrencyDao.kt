package com.allenwang.currency.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.allenwang.currency.data.unity.SupportedCurrency
import io.reactivex.rxjava3.core.Single

@Dao
interface SupportCurrencyDao {

    @Query("SELECT * FROM supported_currencies")
    fun getAllSupportedCurrencies() : Single<List<SupportedCurrency>>

    @Query("SELECT * FROM supported_currencies WHERE id = :id")
    fun getCurrency(id: Int): Single<SupportedCurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SupportedCurrency>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(supportedCurrency: SupportedCurrency)
}

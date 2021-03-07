package com.allenwang.currency.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.allenwang.currency.data.unity.CurrencyQuote
import com.allenwang.currency.data.unity.SupportedCurrency
import com.squareup.moshi.Moshi
import javax.inject.Inject

@Database(entities = [SupportedCurrency::class, CurrencyQuote::class], version = 1)
@TypeConverters(CClassTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun supportedCurrencyDao(): SupportCurrencyDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context, typeConverter: CClassTypeConverter): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context, typeConverter).also { instance = it } }

        private fun buildDatabase(appContext: Context, typeConverter: CClassTypeConverter) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "currency")
                .fallbackToDestructiveMigration()
                .build()
    }
}

package com.allenwang.currency.data.local

import android.os.Build.VERSION_CODES.O
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment.systemContext
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [O])
class AppDatabaseTest {

     @Test
     fun createInstance_AsSingleTon() {
          val instance = AppDatabase.getDatabase(systemContext)
          assertThat(instance, `not`(nullValue()))

          val instance2 = AppDatabase.getDatabase(systemContext)
          assertThat(instance, `is`(instance2))
     }

}
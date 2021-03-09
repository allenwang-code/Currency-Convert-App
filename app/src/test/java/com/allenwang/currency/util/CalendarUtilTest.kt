package com.allenwang.currency.util

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import java.util.*


class CalendarUtilTest {

    @Test
    fun testTimeYesterday (){
        Assert.assertThat(CalendarUtil.getTimeYesterday(),
            CoreMatchers.not(CoreMatchers.nullValue())
        )
    }

}
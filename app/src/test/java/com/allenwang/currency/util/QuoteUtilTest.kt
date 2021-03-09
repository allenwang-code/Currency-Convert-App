package com.allenwang.currency.util

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test

class QuoteUtilTest {
    @Test
    fun testIntAndDoubleCase1() {
        val d = QuoteUtil.calculate(1.21, 2)
        assertThat(d, `is`(2.42))
    }

    @Test
    fun testMaximumInt() {
        QuoteUtil.calculate(999.9, 2147483647)
    }

    @Test
    fun testMinimumInt() {
        QuoteUtil.calculate(999.9, -2147483648)
    }

}
package com.allenwang.currency.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.allenwang.currency.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AppFlowTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainFlowTest() {
        val editText = onView(
            allOf(
                withId(R.id.amount_editText),
                isDisplayed()
            )
        )
        editText.check(matches(withText("1")))

        val button = onView(withId(R.id.chosen_currency_button))
        button.check(matches(isDisplayed()))

        val recyclerView = onView(withId(R.id.quotes_list))
        recyclerView.check(matches(isDisplayed()))

        val appCompatButton = onView(
            allOf(
                withId(R.id.chosen_currency_button),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        Thread.sleep(3000);

        val textView = onView(
            allOf(
                withId(R.id.item_number), withText("AED"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.currency_list),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("AED")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}

package com.wipro.feeddigest

import android.util.Config.LOGD
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.wipro.feeddigest.ui.FeedDigestActivity
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.Description
import org.junit.runner.RunWith


private const val TAG = "test-case"

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val activityRule = ActivityTestRule(FeedDigestActivity::class.java, false, false)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.wipro.feeddigest", appContext.packageName)
    }


    /*
    This test is used to check whether the feed digest activity lanuch successfully or not
     */
    @Test
    fun launchFeedDigestActivity() {
        activityRule.launchActivity(null)
    }

    /*
    This test is used to check after the load data in feed digest recycle view is visible or not
     */
    @Test
    fun checkFeedDigestData() {
        activityRule.launchActivity(null)
        wait_for_seconds(6000, "ms")
        Espresso.onView(withId(R.id.rv_feed_digest))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.rv_feed_digest)).check(matches(withRecyclerViewSize(14)));
    }

    /*
    This test is used to check whether feed digest data is not visible in case of error
     */
    @Test
    fun checkFeedDigestDataIsNotVisible() {
        activityRule.launchActivity(null)
        wait_for_seconds(6000, "ms")
        Espresso.onView(withId(R.id.no_feed_data))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }


    fun wait_for_seconds(duration: Long, units: String) {
        Log.i(TAG, "Waiting for '$duration' '$units'")
        val multiplier = when (units) {
            "millisecond", "ms" -> 1
            "second", "s" -> 1000
            "minute", "m" -> 60 * 1000
            "hour", "h" -> 60 * 60 * 1000
            "day", "d" -> 24 * 60 * 60 * 1000
            else -> 0
        }
        Thread.sleep(duration * multiplier)
    }

    fun withRecyclerViewSize(size: Int): Matcher<View?>? {
        return object : TypeSafeMatcher<View?>() {
            override fun matchesSafely(item: View?): Boolean {
                val actualListSize = (item as RecyclerView).adapter!!.itemCount
                Log.d(TAG, "RecyclerView actual size $actualListSize")
                return actualListSize == size
            }

            override fun describeTo(description: org.hamcrest.Description?) {
                description?.appendText("RecyclerView should have $size items")
            }
        }
    }
}
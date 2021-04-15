package com.wipro.feeddigest

import android.os.Build
import com.wipro.feeddigest.adapter.FeedDigestAdapter
import com.wipro.feeddigest.ui.FeedDigestActivity
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class FeedDigestDummyDataSizeTest {

    private lateinit var activity: FeedDigestActivity

    private lateinit var activityController: ActivityController<FeedDigestActivity>

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    /*This test is used to test by post feed digest list to live data*/
    @Test
    fun checkFeedDigestDataSize() {
        activityController = Robolectric.buildActivity(FeedDigestActivity::class.java)
        activity = activityController.get()
        activityController.create()
        activityController.start()
        activity.feedDigestViewModel?.feedDigestResponse?.postValue(FeedDigestItemFactory().getFeedDigestItem())
        val dataSize = activity.feedDigestAdapter?.itemCount
        assertEquals(dataSize, 15)
    }
}
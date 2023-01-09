package com.wipro.feeddigest

import android.os.Build
import android.view.View
import com.wipro.feeddigest.adapter.FeedDigestAdapter
import com.wipro.feeddigest.ui.FeedDigestActivity
import org.junit.Assert.assertEquals
import org.junit.Before
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
class FeedDigestTest {

    private lateinit var activity: FeedDigestActivity

    private lateinit var activityController: ActivityController<FeedDigestActivity>

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        activityController = Robolectric.buildActivity(FeedDigestActivity::class.java)
        activity = activityController.get()
        activityController.create()
        activityController.start()
    }

    /*This test is used to test by post feed digest list to live data*/
    @Test
    fun checkFeedDigestDataLoaded() {
        activity.feedDigestViewModel?.feedDigestResponse?.postValue(FeedDigestItemFactory().getFeedDigestItem())
        assertEquals(View.VISIBLE, activity.feedUiBinding.rvFeedDigest.visibility)
    }

    /*This test is used to test by post error to live data*/
    @Test
    fun checkFeedDigestError() {
        activity.feedDigestViewModel?.feedDigestResponseError?.postValue(FeedDigestItemFactory().fetFeedDigestError())
        assertEquals(View.VISIBLE, activity.feedUiBinding.noFeedData.visibility)
    }

    /*This test is used to test by post empty feed digest list to live data*/
    @Test
    fun checkFeedDigestNoData() {
        activity.feedDigestViewModel?.feedDigestResponse?.postValue(FeedDigestItemFactory().getEmptyFeedDigestItem())
        assertEquals(View.VISIBLE, activity.feedUiBinding.noFeedData.visibility)
    }

    /*This test is used to test by post feed digest list to live data*/
    @Test
    fun checkFeedDigestDataSize() {
        activity.feedDigestViewModel?.feedDigestResponse?.postValue(FeedDigestItemFactory().getFeedDigestItem())
        val dataSize = activity.feedDigestAdapter?.itemCount
        assertEquals(dataSize, 15)
    }

    /*This test is used to test by title in feed data is correctly updated or not*/
    @Test
    fun checkFeedDigestTitleContent() {
        activity.feedDigestViewModel?.feedDigestResponse?.postValue(FeedDigestItemFactory().getFeedDigestItem())
        //Fetching the title from second position in adapter
        val title =
            (activity.feedUiBinding.rvFeedDigest.adapter as FeedDigestAdapter).feedDigestList[1].title
        assertEquals(title, "Beavers")
    }

    /*This test is used to test by description in feed data is correctly updated or not*/
    @Test
    fun checkFeedDigestDescriptionContent() {
        activity.feedDigestViewModel?.feedDigestResponse?.postValue(FeedDigestItemFactory().getFeedDigestItem())
        //Fetching the title from second position in adapter
        val title =
            (activity.feedUiBinding.rvFeedDigest.adapter as FeedDigestAdapter).feedDigestList[1].description
        assertEquals(
            title,
            "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before final copy is available."
        )
    }
}
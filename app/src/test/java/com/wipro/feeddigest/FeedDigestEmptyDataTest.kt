package com.wipro.feeddigest

import android.os.Build
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.wipro.feeddigest.model.FeedDigest
import com.wipro.feeddigest.ui.FeedDigestActivity
import com.wipro.feeddigest.viewmodel.FeedDigestViewModel
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class FeedDigestEmptyDataTest {

    private lateinit var activity: FeedDigestActivity

    private lateinit var activityController: ActivityController<FeedDigestActivity>

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    /*This test is used to test by post empty feed digest list to live data*/
    @Test
    fun checkFeedDigestNoData() {
        activityController = Robolectric.buildActivity(FeedDigestActivity::class.java)
        activity = activityController.get()
        activityController.create()
        activityController.start()
        activity.feedDigestViewModel?.feedDigestResponse?.postValue(FeedDigestItemFactory().getEmptyFeedDigestItem())
        assertEquals(View.VISIBLE, activity.feedUiBinding.noFeedData.visibility)
    }
}
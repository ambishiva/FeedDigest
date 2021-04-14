package com.wipro.feeddigest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wipro.feeddigest.model.FeedDigestApiResponse
import com.wipro.feeddigest.repository.FeedDigestRepository


private const val TAG = "feed-digest-ui"

/**
 * The ViewModel class to store and manage date data in a lifecycle conscious way.
 * Helper class for the UI controller that is responsible for preparing data for the UI
 */
class FeedDigestViewModel : ViewModel() {

    var feedDigestResponseError = MutableLiveData<Throwable>()
    var feedDigestResponse: MutableLiveData<FeedDigestApiResponse>? = MutableLiveData()

    fun getFeedDigest() {
        FeedDigestRepository.getFeeds { feedDigestApiResponse ->
            if (feedDigestApiResponse.error == null) {
                feedDigestResponse?.postValue(feedDigestApiResponse)
            } else {
                feedDigestResponseError.postValue(feedDigestApiResponse.error)
            }
        }
    }

}
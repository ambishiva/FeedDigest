package com.wipro.feeddigest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wipro.feeddigest.model.FeedDigest
import com.wipro.feeddigest.repository.FeedDigestRepository

/**
 * The ViewModel class to store and manage date data in a lifecycle conscious way.
 * Helper class for the UI controller that is responsible for preparing data for the UI
 */
class FeedDigestViewModel : ViewModel() {

    var feedDigestResponseError = MutableLiveData<Throwable>()
    var feedDigestResponse: MutableLiveData<List<FeedDigest>>? = MutableLiveData()
    var feedDigestTitle: MutableLiveData<String> = MutableLiveData()

    fun getFeedDigest() {
        FeedDigestRepository.getFeeds { feedDigestApiResponse ->
            if (feedDigestApiResponse.error == null) {
                feedDigestResponse?.postValue(feedDigestApiResponse.feedDigest?.feedDigestList)
                feedDigestTitle.postValue(feedDigestApiResponse.feedDigest?.title)
            } else {
                feedDigestResponseError.postValue(feedDigestApiResponse.error)
            }
        }
    }

}
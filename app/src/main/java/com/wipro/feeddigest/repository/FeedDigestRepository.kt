package com.wipro.feeddigest.repository

import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.wipro.feeddigest.model.FeedDigestApiResponse
import com.wipro.feeddigest.model.FeedDigestModel
import com.wipro.feeddigest.utilities.Api

/**
 * This class is responsible for fetching feeds from cloud
 */
object FeedDigestRepository {

    fun getFeeds(callback: (FeedDigestApiResponse) -> Unit) {
        val url = "${Api.deviceBaseUrl}${Api.feedDigestUrl}"
        Rx2AndroidNetworking.get(url)
            .build()
            .getAsObject(
                FeedDigestModel::class.java,
                object : ParsedRequestListener<FeedDigestModel> {
                    override fun onResponse(feedDigest: FeedDigestModel) {
                        // handle response from server
                        callback(FeedDigestApiResponse(feedDigest, error = null))
                    }

                    override fun onError(error: ANError) {
                        // handle error
                        callback(FeedDigestApiResponse(null, error = Throwable(error.message)))
                    }
                })
    }
}
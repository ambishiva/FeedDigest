package com.wipro.feeddigest

import com.wipro.feeddigest.model.FeedDigest


class FeedDigestItemFactory {

    fun getFeedDigestItem(): ArrayList<FeedDigest> {
        val feedDigestList = ArrayList<FeedDigest>()
        for (i in 1..15) {
            feedDigestList.add(
                FeedDigest(
                    description = "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before final copy is available.",
                    imageHref = "www.test.com",
                    title = "Beavers"
                )
            )
        }
        return feedDigestList
    }

    fun getEmptyFeedDigestItem(): ArrayList<FeedDigest> {
        val feedDigestList = ArrayList<FeedDigest>()
        return feedDigestList
    }

    fun fetFeedDigestError(): Throwable {
        return Throwable("Server Error-502")
    }
}
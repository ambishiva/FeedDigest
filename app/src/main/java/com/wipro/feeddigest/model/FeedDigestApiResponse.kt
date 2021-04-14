package com.wipro.feeddigest.model

data class FeedDigestApiResponse(var feedDigest: FeedDigestModel? = null, var error: Throwable? = null)
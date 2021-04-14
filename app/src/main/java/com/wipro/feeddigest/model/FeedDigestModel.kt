package com.wipro.feeddigest.model

import com.google.gson.annotations.SerializedName

data class FeedDigestModel(
    @SerializedName("rows")
    val feedDigestList: ArrayList<FeedDigest>? = null,
    val title: String
)
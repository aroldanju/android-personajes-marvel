package com.aroldan.marvelapp.common.data.model

import com.google.gson.annotations.SerializedName

open class PageEntity <T> (
    @SerializedName("offset") var offset: Int,
    @SerializedName("limit") var limit: Int,
    @SerializedName("total") var total: Int,
    @SerializedName("count") var count: Int,
    @SerializedName("results") var results: List<T>
)

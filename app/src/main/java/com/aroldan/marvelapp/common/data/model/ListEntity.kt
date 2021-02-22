package com.aroldan.marvelapp.common.data.model

import com.google.gson.annotations.SerializedName

data class ListEntity<T>(
    @SerializedName("available") var available: Int,
    @SerializedName("collectionURI") var uri: String,
    @SerializedName("items") var items: List<T>
)
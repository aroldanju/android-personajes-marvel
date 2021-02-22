package com.aroldan.marvelapp.common.data.model

import com.google.gson.annotations.SerializedName

data class UrlEntity(
    @SerializedName("type") var type: String,
    @SerializedName("url") var url: String
)
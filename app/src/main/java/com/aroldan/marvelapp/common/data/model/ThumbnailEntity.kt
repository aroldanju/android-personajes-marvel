package com.aroldan.marvelapp.common.data.model

import com.google.gson.annotations.SerializedName

data class ThumbnailEntity(
    @SerializedName("path") var path: String,
    @SerializedName("extension") var extension: String
)
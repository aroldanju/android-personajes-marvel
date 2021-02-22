package com.aroldan.marvelapp.common.data.model

import com.google.gson.annotations.SerializedName

data class SerieEntity(
    @SerializedName("name") var name: String,
    @SerializedName("resourceURI") var uri: String
)
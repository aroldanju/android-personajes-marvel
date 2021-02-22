package com.aroldan.marvelapp.common.data.model

import com.google.gson.annotations.SerializedName

open class ApiResponse<T>(
    @SerializedName("code") var code: Int,
    @SerializedName("status") var status: String,
    @SerializedName("data") var data: PageEntity<T>
)

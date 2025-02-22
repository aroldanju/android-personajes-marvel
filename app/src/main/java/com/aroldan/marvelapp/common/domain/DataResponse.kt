package com.aroldan.marvelapp.common.domain

sealed class DataResponse <out T: Any> {
    data class Success<out T: Any>(val data: T): DataResponse<T>()
    data class Error(val error: String?): DataResponse<Nothing>()
}

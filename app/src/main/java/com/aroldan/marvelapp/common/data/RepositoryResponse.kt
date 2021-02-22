package com.aroldan.marvelapp.common.data

sealed class RepositoryResponse<T> {
    data class Success<T>(val data: T): RepositoryResponse<T>()
    data class Error(val message: String): RepositoryResponse<Nothing>()
}

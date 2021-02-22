package com.aroldan.marvelapp.common.presentation.model

/*
sealed class ScreenStatus<out T> {
    class Loading: ScreenStatus<Nothing>()
    data class Success<out T>(val data: T): ScreenStatus<T>()
    data class Error(var error: String?): ScreenStatus<Nothing>()
}
*/

sealed class ScreenStatus {
    object Loading: ScreenStatus()
    object Idle: ScreenStatus()
    object Empty: ScreenStatus()
    //object Success: ScreenStatus()
    //object Error: ScreenStatus()
    //class Error(var error: String?): ScreenStatus()
}
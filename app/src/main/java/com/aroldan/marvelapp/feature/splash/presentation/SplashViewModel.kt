package com.aroldan.marvelapp.feature.splash.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aroldan.marvelapp.feature.splash.presentation.model.SplashNavigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel: ViewModel() {

    private val _navigation = MutableLiveData<SplashNavigation>()
    val navigation: LiveData<SplashNavigation> = _navigation

    fun start() {
        _navigation.value = SplashNavigation.None

        viewModelScope.launch {
            val navigationStatus = withContext(Dispatchers.IO) {
                try {
                    Thread.sleep(2500)
                    SplashNavigation.Navigate
                }
                catch (ex: InterruptedException) {
                    SplashNavigation.Navigate
                }
            }

            _navigation.value = navigationStatus
        }
    }

}
package com.aroldan.marvelapp.feature.splash.presentation.model

sealed class SplashNavigation {
    object None: SplashNavigation()
    object Navigate: SplashNavigation()
}
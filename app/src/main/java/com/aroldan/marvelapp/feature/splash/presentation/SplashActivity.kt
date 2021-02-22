package com.aroldan.marvelapp.feature.splash.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.aroldan.marvelapp.common.presentation.navigate
import com.aroldan.marvelapp.databinding.ActivitySplashBinding
import com.aroldan.marvelapp.feature.characterlist.presentation.CharacterListActivity
import com.aroldan.marvelapp.feature.splash.presentation.model.SplashNavigation
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivitySplashBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        initializeViewModel()

        viewModel.start()
    }

    private fun initializeViewModel() {
        viewModel.navigation.observe(this, Observer {
            when (it) {
                is SplashNavigation.Navigate -> navigate<CharacterListActivity>(true)
                else -> {}
            }
        })
    }

}

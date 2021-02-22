package com.aroldan.marvelapp.feature.character.presentation

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.aroldan.marvelapp.common.Constant
import com.aroldan.marvelapp.common.presentation.gone
import com.aroldan.marvelapp.common.presentation.loadFromUrl
import com.aroldan.marvelapp.common.presentation.model.CharacterDisplay
import com.aroldan.marvelapp.common.presentation.model.ScreenStatus
import com.aroldan.marvelapp.common.presentation.navigateBack
import com.aroldan.marvelapp.common.presentation.visible
import com.aroldan.marvelapp.databinding.ActivityCharacterBinding
import com.aroldan.marvelapp.feature.character.presentation.adapter.ComicListAdapter
import org.koin.android.ext.android.inject

class CharacterActivity : AppCompatActivity() {

    private val viewModel: CharacterViewModel by inject()

    private val comicsAdapter = ComicListAdapter()

    private val binding by lazy {
        ActivityCharacterBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindView()
        initializeViewModel()

        viewModel.loadCharacter(intent.extras?.getInt(Constant.ARGUMENT_CHARACTER_ID, 0))
    }

    private fun bindView() {
        setSupportActionBar(binding.toolbar)

        binding.characterComics.adapter = comicsAdapter
    }

    private fun initializeViewModel() {
        viewModel.screenStatus.observe(this, Observer {
            when (it) {
                is ScreenStatus.Idle -> showIdle()
                is ScreenStatus.Loading -> showLoading()
            }
        })

        viewModel.character.observe(this, Observer {
            showCharacter(it)
        })

        viewModel.error.observe(this, Observer {
            showError(it.error)
        })
    }

    private fun showIdle() {
        binding.characterProgressLayout.gone()
    }

    private fun showError(error: String?) {
        navigateBack(Activity.RESULT_CANCELED,
            bundleOf(Constant.ARGUMENT_ERROR_MESSAGE to error)
        )
    }

    private fun showLoading() {
        binding.characterProgressLayout.visible()
    }

    private fun showCharacter(character: CharacterDisplay) {
        binding.characterImage.loadFromUrl(character.image)
        binding.characterToolbarLayout.title = character.name
        binding.characterDescription.text = character.description

        comicsAdapter.data = character.comics
    }

}
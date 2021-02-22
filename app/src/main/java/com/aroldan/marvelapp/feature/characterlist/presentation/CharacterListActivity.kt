package com.aroldan.marvelapp.feature.characterlist.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.aroldan.marvelapp.R
import com.aroldan.marvelapp.common.Constant
import com.aroldan.marvelapp.common.presentation.gone
import com.aroldan.marvelapp.common.presentation.model.CharacterDisplay
import com.aroldan.marvelapp.common.presentation.model.ScreenStatus
import com.aroldan.marvelapp.common.presentation.snackbar
import com.aroldan.marvelapp.common.presentation.view.Pagination
import com.aroldan.marvelapp.common.presentation.visible
import com.aroldan.marvelapp.databinding.ActivityCharacterListBinding
import com.aroldan.marvelapp.feature.character.presentation.CharacterActivity
import com.aroldan.marvelapp.feature.characterlist.presentation.adapter.CharacterListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterListActivity : AppCompatActivity() {

    private val characterListViewModel: CharacterListViewModel by viewModel()

    private lateinit var pagination: Pagination

    private val binding by lazy {
        ActivityCharacterListBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
    }

    private val characterDetailsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_CANCELED) {
            it.data?.getStringExtra(Constant.ARGUMENT_ERROR_MESSAGE)?.let {
                showGeneralError(it)
            }
        }
    }

    private val characterAdapter: CharacterListAdapter = CharacterListAdapter { character ->
        val intent = Intent(this, CharacterActivity::class.java)
        intent.putExtras(bundleOf(Constant.ARGUMENT_CHARACTER_ID to character.id))
        characterDetailsLauncher.launch(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindView()
        initializeViewModel()

        characterListViewModel.loadCharacters()
    }

    private fun bindView() {
        binding.listCharacters.run {
            adapter = characterAdapter

            pagination = Pagination.Builder()
                .with(this)
                .onLoadMoreItemsListener { characterListViewModel.loadCharactersPage() }
                .build()
        }

        binding.characterListRetry.setOnClickListener {
            characterListViewModel.loadCharacters()
        }

        binding.characterListSwipe.setOnRefreshListener {
            characterListViewModel.loadCharacters()
        }
    }

    private fun initializeViewModel() {
        characterListViewModel.screenStatus.observe(this, Observer {
            when (it) {
                is ScreenStatus.Loading -> showLoading()
                is ScreenStatus.Idle -> showIdle()
                is ScreenStatus.Empty -> showEmpty()
            }
        })

        characterListViewModel.characters.observe(this, Observer {
            showCharacters(it)
        })

        characterListViewModel.error.observe(this, Observer {
            showError(it.error)
            pagination.onError()
        })
    }

    private fun showCharacters(characterList: List<CharacterDisplay>) {
        characterAdapter.data = characterList
    }

    private fun showEmpty() {
        binding.characterListSwipe.isRefreshing = false
        binding.characterListProgressLayout.gone()
        binding.characterListNoresultsLayout.visible()
    }

    private fun showGeneralError(error: String?) {
        snackbar(error ?: getString(R.string.unknown_error))?.run {
            show()
        }
    }

    private fun showIdle() {
        binding.characterListProgressLayout.gone()
        binding.characterListSwipe.isRefreshing = false
    }

    private fun showError(error: String?) {
        showGeneralError(error)
    }

    private fun showLoading() {
        binding.characterListProgressLayout.visible()
        binding.characterListNoresultsLayout.gone()
    }

}

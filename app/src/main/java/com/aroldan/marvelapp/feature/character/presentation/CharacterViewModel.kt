package com.aroldan.marvelapp.feature.character.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aroldan.marvelapp.common.domain.model.Character
import com.aroldan.marvelapp.common.domain.model.Error
import com.aroldan.marvelapp.common.presentation.mapper.CharacterDisplayMapper
import com.aroldan.marvelapp.common.presentation.model.ErrorDisplay
import com.aroldan.marvelapp.common.presentation.model.ScreenStatus
import com.aroldan.marvelapp.feature.character.domain.GetCharacterDetailsUseCase
import com.aroldan.marvelapp.common.presentation.model.CharacterDisplay
import kotlinx.coroutines.launch
import timber.log.Timber

class CharacterViewModel(private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
                         private val characterDisplayMapper: CharacterDisplayMapper):
    ViewModel() {

    private val _character = MutableLiveData<CharacterDisplay>()
    val character: LiveData<CharacterDisplay> = _character

    private val _screenStatus = MutableLiveData<ScreenStatus>()
    val screenStatus: LiveData<ScreenStatus> = _screenStatus

    private val _error = MutableLiveData<ErrorDisplay>()
    val error: LiveData<ErrorDisplay> = _error

    fun loadCharacter(characterId: Int?) {

        _screenStatus.value = ScreenStatus.Loading

        viewModelScope.launch {
            getCharacterDetailsUseCase.getCharacter(characterId, object : GetCharacterDetailsUseCase.GetCharacterDetailsCallback {
                override fun onError(error: Error?) {
                    _screenStatus.value = ScreenStatus.Idle

                    _error.value = ErrorDisplay(error = error?.error)
                }

                override fun onSuccess(character: Character) {
                    Timber.i("character comics: ${character.comics}")
                    _character.value = characterDisplayMapper.map(character)

                    _screenStatus.value = ScreenStatus.Idle
                }
            })
        }
    }

}
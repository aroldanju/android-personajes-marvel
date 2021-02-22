package com.aroldan.marvelapp.feature.characterlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aroldan.marvelapp.common.domain.model.Character
import com.aroldan.marvelapp.common.domain.model.Error
import com.aroldan.marvelapp.common.presentation.mapper.CharacterDisplayMapper
import com.aroldan.marvelapp.common.presentation.model.ErrorDisplay
import com.aroldan.marvelapp.common.presentation.model.ScreenStatus
import com.aroldan.marvelapp.feature.characterlist.domain.GetCharacterListUseCase
import com.aroldan.marvelapp.common.presentation.model.CharacterDisplay
import kotlinx.coroutines.launch

class CharacterListViewModel(private val getCharacterListUseCase: GetCharacterListUseCase,
                             private val characterDisplayMapper: CharacterDisplayMapper,
                             private val pageStatus: PageStatus): ViewModel() {

    private var _characters = MutableLiveData<List<CharacterDisplay>>()
    val characters: LiveData<List<CharacterDisplay>> = _characters

    private var _screenStatus = MutableLiveData<ScreenStatus>()
    val screenStatus: LiveData<ScreenStatus> = _screenStatus

    private val _error = MutableLiveData<ErrorDisplay>()
    val error: LiveData<ErrorDisplay> = _error

    fun loadCharactersPage() {
        viewModelScope.launch {

            _screenStatus.value = ScreenStatus.Loading

            getCharacterListUseCase.getCharacterList(pageStatus.offset, pageStatus.limit, object : GetCharacterListUseCase.GetCharacterListCallback {
                override fun onError(error: Error?) {
                    _screenStatus.value = ScreenStatus.Idle
                    _error.value = ErrorDisplay(error = error?.error)
                }

                override fun onSuccess(characters: List<Character>) {
                    pageStatus.characters.addAll(characterDisplayMapper.map(characters))
                    _characters.value = pageStatus.characters
                    pageStatus.offset += pageStatus.limit

                    _screenStatus.value = ScreenStatus.Idle
                }
            })
        }
    }

    fun loadCharacters() {

        // Reset pagination status
        pageStatus.offset = 0
        pageStatus.characters = mutableListOf()

        viewModelScope.launch {

            _screenStatus.value = ScreenStatus.Loading

            getCharacterListUseCase.getCharacterList(pageStatus.offset, pageStatus.limit, object : GetCharacterListUseCase.GetCharacterListCallback {
                override fun onError(error: Error?) {
                    _screenStatus.value = ScreenStatus.Empty
                    _error.value = ErrorDisplay(error = error?.error)
                    _characters.value = listOf()
                }

                override fun onSuccess(characters: List<Character>) {
                    pageStatus.offset += pageStatus.limit
                    pageStatus.characters.addAll(characterDisplayMapper.map(characters))

                    _characters.value = pageStatus.characters
                    _screenStatus.value = ScreenStatus.Idle
                }
            })
        }

    }

}
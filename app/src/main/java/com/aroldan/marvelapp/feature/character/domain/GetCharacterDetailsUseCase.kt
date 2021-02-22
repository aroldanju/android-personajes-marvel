package com.aroldan.marvelapp.feature.character.domain

import com.aroldan.marvelapp.common.domain.CharacterRepositoryContract
import com.aroldan.marvelapp.common.domain.DataResponse
import com.aroldan.marvelapp.common.domain.model.Error
import com.aroldan.marvelapp.common.domain.model.Character
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

class GetCharacterDetailsUseCase(private val characterRepository: CharacterRepositoryContract,
                                 private val dispatcher: CoroutineDispatcher) {

    interface GetCharacterDetailsCallback {
        fun onError(error: Error?)
        fun onSuccess(character: Character)
    }

    suspend fun getCharacter(characterId: Int?, callback: GetCharacterDetailsCallback) {
        if (characterId != null) {
            characterRepository.getCharacterDetails(characterId)
                .flowOn(dispatcher)
                .collect {
                    when (it) {
                        is DataResponse.Success -> callback.onSuccess(it.data)
                        is DataResponse.Error -> callback.onError(Error(it.error))
                    }
                }
        }
        else {
            callback.onError(Error())
        }
    }

}

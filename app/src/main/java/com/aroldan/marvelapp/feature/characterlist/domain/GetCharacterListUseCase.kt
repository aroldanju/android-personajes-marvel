package com.aroldan.marvelapp.feature.characterlist.domain

import com.aroldan.marvelapp.common.domain.CharacterRepositoryContract
import com.aroldan.marvelapp.common.domain.DataResponse
import com.aroldan.marvelapp.common.domain.model.Character
import com.aroldan.marvelapp.common.domain.model.Error
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

class GetCharacterListUseCase(private val characterRepository: CharacterRepositoryContract,
                              private val dispatcher: CoroutineDispatcher) {

    interface GetCharacterListCallback {
        fun onSuccess(characters: List<Character>)
        fun onError(error: Error?)
    }

    //suspend fun getCharacterList(offset: Int, limit: Int, successHandler: (characterList: List<Character>) -> Unit, errorHandler: (message: Error) -> Unit) {
    suspend fun getCharacterList(offset: Int, limit: Int, callback: GetCharacterListCallback) {
        characterRepository.getCharacters(offset, limit)
            .flowOn(dispatcher)
            .collect {
                when (it) {
                    is DataResponse.Success -> callback.onSuccess(it.data)//successHandler(it.data)
                    is DataResponse.Error -> callback.onError(Error(it.error))//errorHandler(Error(it.error))
                }
            }
    }

}
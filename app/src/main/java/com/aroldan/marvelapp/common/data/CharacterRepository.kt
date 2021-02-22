package com.aroldan.marvelapp.common.data

import com.aroldan.marvelapp.common.data.mapper.CharacterEntityMapper
import com.aroldan.marvelapp.common.domain.CharacterRepositoryContract
import com.aroldan.marvelapp.common.domain.DataResponse
import com.aroldan.marvelapp.common.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CharacterRepository(private val marvelApi: MarvelApi,
                          private val characterEntityMapper: CharacterEntityMapper) :

    CharacterRepositoryContract {

    private fun <T : Any> handleSuccess(data: T): DataResponse<T> =
        DataResponse.Success(data)

    private fun handleError(throwable: Throwable) =
        DataResponse.Error(throwable.message)

    override suspend fun getCharacterDetails(characterId: Int): Flow<DataResponse<Character>> =
            flow {
                val response = marvelApi.getCharacterDetails(characterId)
                emit(handleSuccess(characterEntityMapper.map(response.data.results.first())))
            }
            .catch {
                it.printStackTrace()
                emit(handleError(it))
            }

    override suspend fun getCharacters(offset: Int, limit: Int): Flow<DataResponse<List<Character>>> =
            flow {
                val response = marvelApi.getCharacters(offset, limit)
                emit(handleSuccess(characterEntityMapper.map(response.data.results)))
            }
            .catch {
                it.printStackTrace()
                emit(handleError(it))
            }

}

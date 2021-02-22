package com.aroldan.marvelapp.common.domain

import com.aroldan.marvelapp.common.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepositoryContract {

    suspend fun getCharacters(offset: Int, limit: Int): Flow<DataResponse<List<Character>>>

    suspend fun getCharacterDetails(characterId: Int): Flow<DataResponse<Character>>

}

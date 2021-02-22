package com.aroldan.marvelapp.common.data

import com.aroldan.marvelapp.common.data.model.ApiResponse
import com.aroldan.marvelapp.common.data.model.CharacterEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int):
            ApiResponse<CharacterEntity>

    @GET("characters/{characterId}")
    suspend fun getCharacterDetails(
        @Path("characterId") characterId: Int):
            ApiResponse<CharacterEntity>

}
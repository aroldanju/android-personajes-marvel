package com.aroldan.marvelapp.common.data

import com.aroldan.marvelapp.common.data.mapper.CharacterEntityMapper
import com.aroldan.marvelapp.common.data.model.ApiResponse
import com.aroldan.marvelapp.common.data.model.PageEntity
import com.aroldan.marvelapp.common.domain.CharacterRepositoryContract
import com.aroldan.marvelapp.common.domain.DataResponse
import com.aroldan.marvelapp.util.DataProvider
import com.aroldan.marvelapp.util.DomainProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class CharacterRepositoryTest {

    @RelaxedMockK private lateinit var marvelApi: MarvelApi
    @RelaxedMockK private lateinit var characterEntityMapper: CharacterEntityMapper

    private lateinit var characterRepositoryTested: CharacterRepositoryContract

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        characterRepositoryTested = CharacterRepository(marvelApi, characterEntityMapper)
    }

    @Test
    fun `fetching characters returns error`() {
        coEvery { marvelApi.getCharacters(any(), any()) } throws IOException("Exception")

        dispatcher.runBlockingTest {
            val flowTested = characterRepositoryTested.getCharacters(0, 0)

            assertEquals(DataResponse.Error("Exception"), flowTested.first())
        }
    }

    @Test
    fun `fetching characters returns success`() {

        val characterEntityList = DataProvider.provideCharacterList()
        val characterDomainList = DomainProvider.provideCharacterList()

        coEvery { marvelApi.getCharacters(any(), any()) } answers {
            ApiResponse(code = 200, status = "OK", data = PageEntity(offset = 0, limit = 20, total = 100, count = 20, results = characterEntityList))
        }

        coEvery { characterEntityMapper.map(characterEntityList) } answers { characterDomainList }

        runBlockingTest {
            val flowTested = characterRepositoryTested.getCharacters(0, 0)

            assertEquals(DataResponse.Success(characterDomainList), flowTested.first())
        }
    }

    @Test
    fun `fetching character details returns error`() {
        coEvery { marvelApi.getCharacterDetails(any()) } throws IOException("Exception")

        dispatcher.runBlockingTest {
            val flowTested = characterRepositoryTested.getCharacterDetails(0)

            assertEquals(DataResponse.Error("Exception"), flowTested.first())
        }
    }

    @Test
    fun `fetching character details returns success`() {
        val characterEntityList = listOf(DataProvider.provideCharacter())
        val characterDomain = DomainProvider.provideCharacter()

        coEvery { marvelApi.getCharacterDetails(any()) } answers {
            ApiResponse(code = 200, status = "OK", data = PageEntity(offset = 0, limit = 20, total = 100, count = 1, results = characterEntityList))
        }

        coEvery { characterEntityMapper.map(characterEntityList.first()) } answers { characterDomain }

        runBlockingTest {
            val flowTested = characterRepositoryTested.getCharacterDetails(0)

            assertEquals(DataResponse.Success(characterDomain), flowTested.first())
        }
    }

}
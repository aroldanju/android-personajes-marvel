package com.aroldan.marvelapp.feature.characterlist.domain

import com.aroldan.marvelapp.common.domain.CharacterRepositoryContract
import com.aroldan.marvelapp.common.domain.DataResponse
import com.aroldan.marvelapp.common.domain.model.Character
import com.aroldan.marvelapp.feature.character.domain.GetCharacterDetailsUseCase
import com.aroldan.marvelapp.util.DomainProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
class GetCharacterListUseCaseTest {

    @RelaxedMockK
    private lateinit var characterRepository: CharacterRepositoryContract

    @RelaxedMockK
    private lateinit var getCharacterListUseCaseTested: GetCharacterListUseCase

    @RelaxedMockK
    private lateinit var callback: GetCharacterListUseCase.GetCharacterListCallback

    private val coroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        getCharacterListUseCaseTested = GetCharacterListUseCase(
            characterRepository = characterRepository,
            dispatcher = coroutineDispatcher)
    }

    @Test
    fun `when repository returns an error`() {
        coEvery { characterRepository.getCharacters(any(), any()) } answers { flow {
            emit(DataResponse.Error("Error")) }
        }

        runBlockingTest {

            getCharacterListUseCaseTested.getCharacterList(0, 0, callback)

            coVerify(exactly = 1) {
                callback.onError(any())
            }
        }
    }

    @Test
    fun `when repository returns success`() {
        coEvery { characterRepository.getCharacters(any(), any()) } answers { flow {
            emit(DataResponse.Success(DomainProvider.provideCharacterList())) }
        }

        runBlockingTest {

            getCharacterListUseCaseTested.getCharacterList(0, 0, callback)

            coVerify(exactly = 1) {
                callback.onSuccess(any())
            }
        }
    }

}
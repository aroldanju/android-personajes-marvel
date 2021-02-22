package com.aroldan.marvelapp.feature.character.domain

import com.aroldan.marvelapp.common.domain.CharacterRepositoryContract
import com.aroldan.marvelapp.common.domain.DataResponse
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

@ExperimentalCoroutinesApi
class GetCharacterDetailsUseCaseTest {

    @RelaxedMockK
    private lateinit var characterRepository: CharacterRepositoryContract

    @RelaxedMockK
    private lateinit var callback: GetCharacterDetailsUseCase.GetCharacterDetailsCallback

    private lateinit var getCharacterDetailsUseCaseTested: GetCharacterDetailsUseCase

    private val coroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        getCharacterDetailsUseCaseTested = GetCharacterDetailsUseCase(
            characterRepository = characterRepository,
            dispatcher = coroutineDispatcher
        )
    }

    @Test
    fun `given an invalid id then repository must not be called`() {
        val invalidId = null

        runBlockingTest {
            getCharacterDetailsUseCaseTested.getCharacter(invalidId, callback)

            coVerify(exactly = 0) {
                characterRepository.getCharacterDetails(any())
            }
        }
    }


    @Test
    fun `given a valid id then repository must be called`() {
        val validId = 1

        coEvery { characterRepository.getCharacterDetails(any()) } answers { flow {} }

        runBlockingTest {
            getCharacterDetailsUseCaseTested.getCharacter(validId, callback)

            coVerify(exactly = 1) {
                characterRepository.getCharacterDetails(any())
            }
        }
    }

    @Test
    fun `when repository returns an error`() {

        coEvery { characterRepository.getCharacterDetails(any()) } answers { flow { emit(DataResponse.Error("Error")) } }

        runBlockingTest {

            getCharacterDetailsUseCaseTested.getCharacter(1, callback)

            coVerify(exactly = 1) {
                callback.onError(any())
            }
        }
    }

    @Test
    fun `when repository returns success`() {
        val character = DomainProvider.provideCharacter()

        coEvery { characterRepository.getCharacterDetails(any()) } answers {
            flow { emit(DataResponse.Success(character)) }
        }

        runBlockingTest {

            getCharacterDetailsUseCaseTested.getCharacter(1, callback)

            coVerify(exactly = 1) {
                callback.onSuccess(any())
            }
        }
    }

}
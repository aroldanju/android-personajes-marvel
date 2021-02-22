package com.aroldan.marvelapp.feature.character.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.aroldan.marvelapp.common.domain.model.Error
import com.aroldan.marvelapp.common.presentation.mapper.CharacterDisplayMapper
import com.aroldan.marvelapp.common.presentation.model.ScreenStatus
import com.aroldan.marvelapp.feature.character.domain.GetCharacterDetailsUseCase
import com.aroldan.marvelapp.common.presentation.model.CharacterDisplay
import com.aroldan.marvelapp.util.DomainProvider
import com.aroldan.marvelapp.util.PresentationProvider
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterViewModelTest {

    @Rule
    @JvmField
    var testRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @RelaxedMockK private lateinit var getCharacterDetailsUseCase: GetCharacterDetailsUseCase
    @RelaxedMockK private lateinit var characterDisplayMapper: CharacterDisplayMapper

    private lateinit var characterDetailsViewModelTested: CharacterViewModel

    @RelaxedMockK private lateinit var screenStatusObserver: Observer<ScreenStatus>
    private val screenStatusCaptor = mutableListOf<ScreenStatus>()

    @RelaxedMockK private lateinit var characterObserver: Observer<CharacterDisplay>

    private val callbackCaptor = slot<GetCharacterDetailsUseCase.GetCharacterDetailsCallback>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        Dispatchers.setMain(dispatcher)

        characterDetailsViewModelTested = CharacterViewModel(
            getCharacterDetailsUseCase = getCharacterDetailsUseCase,
            characterDisplayMapper = characterDisplayMapper)

        characterDetailsViewModelTested.screenStatus.observeForever(screenStatusObserver)
        every { screenStatusObserver.onChanged(capture(screenStatusCaptor)) } just Runs

        characterDetailsViewModelTested.character.observeForever(characterObserver)
    }

    private fun verifyScreenStatus(slot: Int, expectedScreenStatus: ScreenStatus) {
        verify { screenStatusObserver.onChanged(capture(screenStatusCaptor)) }
        Assert.assertEquals(screenStatusCaptor[slot], expectedScreenStatus)
    }

    @Test
    fun `when loading character list getting error response`() {

        characterDetailsViewModelTested.loadCharacter(0)

        dispatcher.runBlockingTest {
            coVerify { getCharacterDetailsUseCase.getCharacter(any(), capture(callbackCaptor)) }
            callbackCaptor.captured.onError(Error("Error"))
            verifyScreenStatus(0, ScreenStatus.Loading)
            verifyScreenStatus(1, ScreenStatus.Idle)
        }
    }

    @Test
    fun `when loading character list getting success response`() {

        val characterDomain = DomainProvider.provideCharacter()
        val characterDisplay = PresentationProvider.provideCharacter()

        every { characterDisplayMapper.map(characterDomain) } answers { characterDisplay }

        characterDetailsViewModelTested.loadCharacter(0)

        dispatcher.runBlockingTest {
            coVerify { getCharacterDetailsUseCase.getCharacter(any(), capture(callbackCaptor)) }
            callbackCaptor.captured.onSuccess(characterDomain)

            verifyScreenStatus(0, ScreenStatus.Loading)
            verifyScreenStatus(1, ScreenStatus.Idle)

            verify { characterObserver.onChanged(characterDisplay) }
        }
    }

}
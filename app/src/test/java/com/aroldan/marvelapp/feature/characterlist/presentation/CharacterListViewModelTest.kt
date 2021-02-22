package com.aroldan.marvelapp.feature.characterlist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.aroldan.marvelapp.common.domain.model.Error
import com.aroldan.marvelapp.common.presentation.mapper.CharacterDisplayMapper
import com.aroldan.marvelapp.common.presentation.model.ScreenStatus
import com.aroldan.marvelapp.feature.characterlist.domain.GetCharacterListUseCase
import com.aroldan.marvelapp.common.presentation.model.CharacterDisplay
import com.aroldan.marvelapp.common.presentation.model.ErrorDisplay
import com.aroldan.marvelapp.util.DataProvider
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
class CharacterListViewModelTest {

    @Rule
    @JvmField
    var testRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @RelaxedMockK private lateinit var getCharacterListUseCase: GetCharacterListUseCase
    @RelaxedMockK private lateinit var characterDisplayMapper: CharacterDisplayMapper

    private lateinit var characterListViewModelTested: CharacterListViewModel

    @RelaxedMockK private lateinit var screenStatusObserver: Observer<ScreenStatus>
    private val screenStatusCaptor = mutableListOf<ScreenStatus>()

    @RelaxedMockK private lateinit var characterListObserver: Observer<List<CharacterDisplay>>
    @RelaxedMockK private lateinit var errorObserver: Observer<ErrorDisplay>

    private val callbackCaptor = slot<GetCharacterListUseCase.GetCharacterListCallback>()

    private val pageStatus = PageStatus(offset = 0, limit = 20)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        Dispatchers.setMain(dispatcher)

        characterListViewModelTested = CharacterListViewModel(
            getCharacterListUseCase = getCharacterListUseCase,
            characterDisplayMapper = characterDisplayMapper,
            pageStatus = pageStatus)

        characterListViewModelTested.screenStatus.observeForever(screenStatusObserver)
        every { screenStatusObserver.onChanged(capture(screenStatusCaptor)) } just Runs

        characterListViewModelTested.characters.observeForever(characterListObserver)
        characterListViewModelTested.error.observeForever(errorObserver)
    }

    private fun verifyScreenStatus(slot: Int, expectedScreenStatus: ScreenStatus) {
        verify { screenStatusObserver.onChanged(capture(screenStatusCaptor)) }
        Assert.assertEquals(screenStatusCaptor[slot], expectedScreenStatus)
    }

    @Test
    fun `when loading character list getting error response`() {

        characterListViewModelTested.loadCharacters()

        dispatcher.runBlockingTest {
            coVerify { getCharacterListUseCase.getCharacterList(any(), any(), capture(callbackCaptor)) }
            callbackCaptor.captured.onError(Error("Error"))
            verifyScreenStatus(0, ScreenStatus.Loading)
            verifyScreenStatus(1, ScreenStatus.Empty)
        }
    }

    @Test
    fun `when loading character list getting success response`() {

        val characterDomainList = DomainProvider.provideCharacterList()
        val characterDisplayList = PresentationProvider.provideCharacterList()

        every { characterDisplayMapper.map(characterDomainList) } answers { characterDisplayList }

        characterListViewModelTested.loadCharacters()

        dispatcher.runBlockingTest {

            Assert.assertEquals(0, pageStatus.offset)

            coVerify { getCharacterListUseCase.getCharacterList(any(), any(), capture(callbackCaptor)) }
            callbackCaptor.captured.onSuccess(characterDomainList)

            verifyScreenStatus(0, ScreenStatus.Loading)
            verifyScreenStatus(1, ScreenStatus.Idle)

            verify { characterListObserver.onChanged(characterDisplayList) }

        }
    }


    @Test
    fun `when loading character list status advance`() {

        val characterDomainList = DomainProvider.provideCharacterList()
        val characterDisplayList = PresentationProvider.provideCharacterList()

        every { characterDisplayMapper.map(characterDomainList) } answers { characterDisplayList }

        characterListViewModelTested.loadCharacters()

        dispatcher.runBlockingTest {

            Assert.assertEquals(0, pageStatus.offset)
            Assert.assertEquals(mutableListOf<CharacterDisplay>(), pageStatus.characters)

            coVerify { getCharacterListUseCase.getCharacterList(any(), any(), capture(callbackCaptor)) }
            callbackCaptor.captured.onSuccess(characterDomainList)

            Assert.assertEquals(pageStatus.limit * 1, pageStatus.offset)
            Assert.assertEquals(characterDisplayList, pageStatus.characters)
        }
    }

    @Test
    fun `when loading character list page status advance`() {

        val characterDomainList = DomainProvider.provideCharacterList()
        val characterDisplayList = PresentationProvider.provideCharacterList()

        every { characterDisplayMapper.map(characterDomainList) } answers { characterDisplayList }

        characterListViewModelTested.loadCharactersPage()

        dispatcher.runBlockingTest {

            Assert.assertEquals(0, pageStatus.offset)
            Assert.assertEquals(mutableListOf<CharacterDisplay>(), pageStatus.characters)

            coVerify { getCharacterListUseCase.getCharacterList(any(), any(), capture(callbackCaptor)) }
            callbackCaptor.captured.onSuccess(characterDomainList)

            Assert.assertEquals(pageStatus.limit * 1, pageStatus.offset)
            Assert.assertEquals(characterDisplayList, pageStatus.characters)
        }
    }

    @Test
    fun `when loading character list page getting error response`() {

        characterListViewModelTested.loadCharactersPage()

        dispatcher.runBlockingTest {
            coVerify { getCharacterListUseCase.getCharacterList(any(), any(), capture(callbackCaptor)) }
            callbackCaptor.captured.onError(Error("Error"))
            verifyScreenStatus(0, ScreenStatus.Loading)
            verifyScreenStatus(1, ScreenStatus.Idle)
        }
    }

    @Test
    fun `when loading character list page getting success response`() {

        val characterDomainList = DomainProvider.provideCharacterList()
        val characterDisplayList = PresentationProvider.provideCharacterList()

        every { characterDisplayMapper.map(characterDomainList) } answers { characterDisplayList }

        characterListViewModelTested.loadCharactersPage()

        dispatcher.runBlockingTest {

            Assert.assertEquals(0, pageStatus.offset)

            coVerify { getCharacterListUseCase.getCharacterList(any(), any(), capture(callbackCaptor)) }
            callbackCaptor.captured.onSuccess(characterDomainList)

            verifyScreenStatus(0, ScreenStatus.Loading)
            verifyScreenStatus(1, ScreenStatus.Idle)

            verify { characterListObserver.onChanged(characterDisplayList) }

        }
    }


}
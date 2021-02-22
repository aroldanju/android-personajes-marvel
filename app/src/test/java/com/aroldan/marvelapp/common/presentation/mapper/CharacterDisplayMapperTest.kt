package com.aroldan.marvelapp.common.presentation.mapper

import com.aroldan.marvelapp.util.DomainProvider
import com.aroldan.marvelapp.util.PresentationProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CharacterDisplayMapperTest {

    @RelaxedMockK private lateinit var comicDisplayMapper: ComicDisplayMapper

    private lateinit var characterDisplayMapperTested: CharacterDisplayMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        characterDisplayMapperTested = CharacterDisplayMapper(comicDisplayMapper = comicDisplayMapper)

        every { comicDisplayMapper.map(DomainProvider.provideComicList()) } answers {
            PresentationProvider.provideComicList()
        }
    }

    @Test
    fun `given character domain list transform to character display list`() {
        val characterDomainList = DomainProvider.provideCharacterList()
        val expectedCharacterDisplayList = PresentationProvider.provideCharacterList()

        val output = characterDisplayMapperTested.map(characterDomainList)

        Assert.assertEquals(expectedCharacterDisplayList, output)
    }

}
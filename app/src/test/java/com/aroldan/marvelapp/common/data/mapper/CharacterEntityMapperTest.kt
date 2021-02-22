package com.aroldan.marvelapp.common.data.mapper

import com.aroldan.marvelapp.util.DataProvider
import com.aroldan.marvelapp.util.DomainProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CharacterEntityMapperTest {

    @RelaxedMockK private lateinit var comicEntityMapper: ComicEntityMapper

    private lateinit var characterEntityMapperTested: CharacterEntityMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        characterEntityMapperTested = CharacterEntityMapper(comicEntityMapper)

        every { comicEntityMapper.map(DataProvider.provideComicList()) } answers {
            DomainProvider.provideComicList()
        }
    }

    @Test
    fun `given character entity list transform to character domain list`() {
        val characterEntityList = DataProvider.provideCharacterList()
        val expectedCharacterDomainList = DomainProvider.provideCharacterList()

        val output = characterEntityMapperTested.map(characterEntityList)

        Assert.assertEquals(expectedCharacterDomainList, output)
    }

}
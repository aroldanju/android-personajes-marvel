package com.aroldan.marvelapp.common.presentation.mapper

import com.aroldan.marvelapp.util.DomainProvider
import com.aroldan.marvelapp.util.PresentationProvider
import org.junit.Assert
import org.junit.Test

class ComicDisplayMapperTest {

    private val comicDisplayMapperTested: ComicDisplayMapper = ComicDisplayMapper()

    @Test
    fun `given comic domain list transform to comic display list`() {
        val comicDomainList = DomainProvider.provideComicList()
        val expectedComicDisplayList = PresentationProvider.provideComicList()

        val output = comicDisplayMapperTested.map(comicDomainList)

        Assert.assertEquals(expectedComicDisplayList, output)
    }

}
package com.aroldan.marvelapp.common.data.mapper

import com.aroldan.marvelapp.util.DataProvider
import com.aroldan.marvelapp.util.DomainProvider
import org.junit.Assert
import org.junit.Test

class ComicEntityMapperTest {

    private val comicEntityMapperTested = ComicEntityMapper()

    @Test
    fun `given comic entity list transform to comic domain list`() {
        val comicEntityList = DataProvider.provideComicList()
        val expectedComicDomainList = DomainProvider.provideComicList()

        val output = comicEntityMapperTested.map(comicEntityList)

        Assert.assertEquals(expectedComicDomainList, output)
    }

}
package com.aroldan.marvelapp.common.presentation.mapper

import com.aroldan.marvelapp.common.domain.model.Error
import com.aroldan.marvelapp.common.presentation.model.ErrorDisplay
import org.junit.Assert
import org.junit.Test

class ErrorDisplayMapperTest {

    private val errorDisplayMapperTested = ErrorDisplayMapper()

    @Test
    fun `given error domain list transform to error display list`() {
        val errorDomainList = listOf(
            Error(error = "Error"),
            Error(error = "Another error")
        )

        val expectedErrorDisplayList = listOf(
            ErrorDisplay(error = "Error"),
            ErrorDisplay(error = "Another error")
        )

        val output = errorDisplayMapperTested.map(errorDomainList)

        Assert.assertEquals(expectedErrorDisplayList, output)
    }

}
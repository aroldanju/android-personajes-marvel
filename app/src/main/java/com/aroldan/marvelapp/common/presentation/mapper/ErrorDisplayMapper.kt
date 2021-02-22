package com.aroldan.marvelapp.common.presentation.mapper

import com.aroldan.marvelapp.common.domain.model.Error
import com.aroldan.marvelapp.common.presentation.model.ErrorDisplay

class ErrorDisplayMapper : DisplayMapper<Error, ErrorDisplay>() {

    override fun map(entity: Error): ErrorDisplay =
        ErrorDisplay(
            error = entity.error
        )

}
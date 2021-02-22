package com.aroldan.marvelapp.common.presentation.mapper

import com.aroldan.marvelapp.common.domain.model.Comic
import com.aroldan.marvelapp.common.presentation.model.ComicDisplay

class ComicDisplayMapper : DisplayMapper<Comic, ComicDisplay>() {

    override fun map(entity: Comic): ComicDisplay =
        ComicDisplay(
            name = entity.name,
            uri = entity.uri
        )

}
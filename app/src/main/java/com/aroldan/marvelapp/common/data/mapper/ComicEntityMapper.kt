package com.aroldan.marvelapp.common.data.mapper

import com.aroldan.marvelapp.common.data.model.ComicEntity
import com.aroldan.marvelapp.common.domain.model.Comic

class ComicEntityMapper : EntityMapper<ComicEntity, Comic>() {

    override fun map(entity: ComicEntity): Comic =
        Comic(
            name = entity.name,
            uri = entity.uri
        )

}
package com.aroldan.marvelapp.common.data.mapper

import com.aroldan.marvelapp.common.data.model.CharacterEntity
import com.aroldan.marvelapp.common.domain.model.Character

class CharacterEntityMapper (private val comicEntityMapper: ComicEntityMapper) : EntityMapper<CharacterEntity, Character>() {

    override fun map(entity: CharacterEntity): Character =
            Character(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                image = "${entity.thumbnail.path}.${entity.thumbnail.extension}",
                comics = comicEntityMapper.map(entity.comics.items)
            )

}
package com.aroldan.marvelapp.common.presentation.mapper

import com.aroldan.marvelapp.common.domain.model.Character
import com.aroldan.marvelapp.common.presentation.model.CharacterDisplay

class CharacterDisplayMapper (private val comicDisplayMapper: ComicDisplayMapper) : DisplayMapper<Character, CharacterDisplay>() {

    override fun map(entity: Character): CharacterDisplay =
        CharacterDisplay(
            id = entity.id,
            image = entity.image,
            name = entity.name,
            description = entity.description,
            comics = comicDisplayMapper.map(entity.comics)
        )

}
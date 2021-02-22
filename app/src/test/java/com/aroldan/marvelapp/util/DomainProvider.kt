package com.aroldan.marvelapp.util

import com.aroldan.marvelapp.common.domain.model.Character
import com.aroldan.marvelapp.common.domain.model.Comic

object DomainProvider : Provider<Character, Comic> {

    override fun provideCharacterList(): List<Character> = listOf(
        Character(
            id = 0,
            name = "Name",
            description = "Description",
            image = "https://path/to/image.png",
            comics = provideComicList()
        ),
        Character(
            id = 1,
            name = "Name 2",
            description = "Description 2",
            image = "https://another/path/to/image.jpg",
            comics = provideComicList()
        )
    )

    override fun provideComicList(): List<Comic> = listOf(
        Comic(name = "Comic name #1", uri = "https://uri.to/comic1"),
        Comic(name = "Comic name #2", uri = "https://uri.to/comic2")
    )

    override fun provideComic(): Comic = provideComicList()[0]

    override fun provideCharacter(): Character = provideCharacterList()[0]

}
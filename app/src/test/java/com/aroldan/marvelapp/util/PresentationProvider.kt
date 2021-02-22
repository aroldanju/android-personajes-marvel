package com.aroldan.marvelapp.util

import com.aroldan.marvelapp.common.presentation.model.CharacterDisplay
import com.aroldan.marvelapp.common.presentation.model.ComicDisplay

object PresentationProvider : Provider<CharacterDisplay, ComicDisplay> {

    override fun provideCharacterList(): List<CharacterDisplay> = listOf(
        CharacterDisplay(
            id = 0,
            name = "Name",
            description = "Description",
            image = "https://path/to/image.png",
            comics = provideComicList()
        ),
        CharacterDisplay(
            id = 1,
            name = "Name 2",
            description = "Description 2",
            image = "https://another/path/to/image.jpg",
            comics = provideComicList()
        )
    )

    override fun provideComicList(): List<ComicDisplay> = listOf(
        ComicDisplay(name = "Comic name #1", uri = "https://uri.to/comic1"),
        ComicDisplay(name = "Comic name #2", uri = "https://uri.to/comic2")
    )

    override fun provideComic(): ComicDisplay = provideComicList()[0]

    override fun provideCharacter(): CharacterDisplay = provideCharacterList()[0]

}
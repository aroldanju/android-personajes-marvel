package com.aroldan.marvelapp.util

import com.aroldan.marvelapp.common.data.model.CharacterEntity
import com.aroldan.marvelapp.common.data.model.ComicEntity
import com.aroldan.marvelapp.common.data.model.ListEntity
import com.aroldan.marvelapp.common.data.model.ThumbnailEntity

object DataProvider : Provider<CharacterEntity, ComicEntity> {

    override fun provideCharacterList(): List<CharacterEntity> = listOf(
        CharacterEntity(
            id = 0,
            name = "Name",
            description = "Description",
            thumbnail = ThumbnailEntity(path = "https://path/to/image", extension = "png"),
            comics = ListEntity(2, "uri", provideComicList())
        ),
        CharacterEntity(
            id = 1,
            name = "Name 2",
            description = "Description 2",
            thumbnail = ThumbnailEntity(path = "https://another/path/to/image", extension = "jpg"),
            comics = ListEntity(2, "uri", provideComicList())
        )
    )

    override fun provideComicList(): List<ComicEntity> = listOf(
        ComicEntity(name = "Comic name #1", uri = "https://uri.to/comic1"),
        ComicEntity(name = "Comic name #2", uri = "https://uri.to/comic2")
    )

    override fun provideComic(): ComicEntity = provideComicList()[0]

    override fun provideCharacter(): CharacterEntity = provideCharacterList()[0]

}
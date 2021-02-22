package com.aroldan.marvelapp.util

interface Provider <Character, Comic> {

    fun provideCharacterList(): List<Character>

    fun provideCharacter(): Character

    fun provideComicList(): List<Comic>

    fun provideComic(): Comic

}
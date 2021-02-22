package com.aroldan.marvelapp.common.presentation.model

data class CharacterDisplay(
    var id: Int?,
    var name: String?,
    var image: String?,
    var description: String?,
    var comics: List<ComicDisplay>
)
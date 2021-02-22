package com.aroldan.marvelapp.feature.characterlist.presentation

import com.aroldan.marvelapp.common.presentation.model.CharacterDisplay

data class PageStatus(
    var offset: Int = 0,
    var limit: Int = 20,
    var characters: MutableList<CharacterDisplay> = mutableListOf()
)

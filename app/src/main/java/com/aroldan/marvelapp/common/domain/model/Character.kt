package com.aroldan.marvelapp.common.domain.model

data class Character(
    var id: Int,
    var name: String,
    var description: String,
    var image: String?,
    var comics: List<Comic>
)

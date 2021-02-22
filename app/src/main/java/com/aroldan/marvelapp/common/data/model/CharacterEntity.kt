package com.aroldan.marvelapp.common.data.model

import com.google.gson.annotations.SerializedName

data class CharacterEntity(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("thumbnail") var thumbnail: ThumbnailEntity,
    @SerializedName("comics") var comics: ListEntity<ComicEntity>
    /*
    @SerializedName("series") var series: ListEntity<SerieEntity>,
    @SerializedName("stories") var stories: ListEntity<StoryEntity>,
    @SerializedName("urls") var urls: List<UrlEntity>
    */
)

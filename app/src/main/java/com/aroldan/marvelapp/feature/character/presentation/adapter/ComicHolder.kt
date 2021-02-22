package com.aroldan.marvelapp.feature.character.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.aroldan.marvelapp.common.presentation.model.ComicDisplay
import com.aroldan.marvelapp.databinding.ItemComicBinding

class ComicHolder(private val binding: ItemComicBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(comic: ComicDisplay) {
        with(binding) {
            itemComicName.text = comic.name
            itemComicUri.text = comic.uri
        }
    }

}
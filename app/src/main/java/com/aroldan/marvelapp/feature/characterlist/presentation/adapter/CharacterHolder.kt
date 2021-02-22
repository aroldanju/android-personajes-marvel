package com.aroldan.marvelapp.feature.characterlist.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.aroldan.marvelapp.common.presentation.loadFromUrl
import com.aroldan.marvelapp.common.presentation.model.CharacterDisplay
import com.aroldan.marvelapp.databinding.ItemCharacterBinding

class CharacterHolder(private val binding: ItemCharacterBinding,
                      private val onCharacterClickListener: (character: CharacterDisplay) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(character: CharacterDisplay) {
        with(binding) {
            itemCharacterImage.loadFromUrl(character.image)
            itemCharacterName.text = character.name
            itemCharacterContainer.setOnClickListener {
                onCharacterClickListener(character)
            }
        }
    }

}
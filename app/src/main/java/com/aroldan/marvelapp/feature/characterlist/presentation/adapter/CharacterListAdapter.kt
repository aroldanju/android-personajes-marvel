package com.aroldan.marvelapp.feature.characterlist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aroldan.marvelapp.R
import com.aroldan.marvelapp.common.presentation.model.CharacterDisplay
import com.aroldan.marvelapp.databinding.ItemCharacterBinding
import kotlin.properties.Delegates

class CharacterListAdapter (private val onCharacterClickListener: (characterDisplay: CharacterDisplay) -> Unit) :
    RecyclerView.Adapter<CharacterHolder>() {

    var data: List<CharacterDisplay> by Delegates.observable(emptyList()) { _, oldList, newList ->
        DiffUtil.calculateDiff(object: DiffUtil.Callback() {
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
            }

            override fun getNewListSize(): Int = newList.size

            override fun getOldListSize(): Int = oldList.size
        }).also {
            it.dispatchUpdatesTo(this)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        data[position].let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder =
        CharacterHolder(
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onCharacterClickListener)

}
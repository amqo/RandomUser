package com.amqo.randomuser.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amqo.randomuser.databinding.ItemRandomUserBinding
import com.amqo.randomuser.db.entity.RandomUserEntry

class RandomUserViewHolder(
    private val binding: ItemRandomUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): RandomUserViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemRandomUserBinding.inflate(layoutInflater, parent, false)
            return RandomUserViewHolder(itemBinding)
        }
    }

    fun bind(randomUser: RandomUserEntry) {
        binding.randomUser = randomUser
        binding.executePendingBindings()
    }
}
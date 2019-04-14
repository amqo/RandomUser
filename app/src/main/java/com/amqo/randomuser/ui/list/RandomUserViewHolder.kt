package com.amqo.randomuser.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.databinding.RandomUserItemBinding

class RandomUserViewHolder(
    private val binding: RandomUserItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): RandomUserViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemBinding = RandomUserItemBinding.inflate(layoutInflater, parent, false)
            return RandomUserViewHolder(itemBinding)
        }
    }

    fun bind(randomUser: RandomUserEntry) {
        binding.randomUser = randomUser
        binding.executePendingBindings()
    }
}
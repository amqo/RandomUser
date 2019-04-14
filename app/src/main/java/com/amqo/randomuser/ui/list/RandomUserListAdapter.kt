package com.amqo.randomuser.ui.list

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import kotlinx.android.synthetic.main.random_user_item.view.*

class RandomUserListAdapter(
    private val randomUsersListener: RandomUsersListener
) : PagedListAdapter<RandomUserEntry, RandomUserViewHolder>(randomUserDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomUserViewHolder {
        return RandomUserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RandomUserViewHolder, position: Int) {
        val randomUser = getItem(position)
        randomUser?.let {
            holder.itemView.setOnClickListener {
                randomUsersListener.onRandomUserSelected(randomUser)
            }
            holder.itemView.remove_user_button.setOnClickListener {
                randomUsersListener.onRemoveRandomUser(randomUser)
            }
            holder.bind(randomUser)
        }
    }

    companion object {

        val randomUserDiffCallback = object : DiffUtil.ItemCallback<RandomUserEntry>() {
            override fun areItemsTheSame(oldItem: RandomUserEntry, newItem: RandomUserEntry): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: RandomUserEntry, newItem: RandomUserEntry): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface RandomUsersListener {

        fun onRandomUserSelected(randomUser: RandomUserEntry)

        fun onRemoveRandomUser(randomUser: RandomUserEntry)
    }
}
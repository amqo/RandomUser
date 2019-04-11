package com.amqo.randomuser.ui.list

import com.amqo.randomuser.R
import com.amqo.randomuser.db.entity.RandomUserEntry
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_list_content.*

class RandomUserItem(
    val randomUserEntry: RandomUserEntry
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            id_text.text = randomUserEntry.getFullName()
            content.text = randomUserEntry.email
        }
    }

    override fun getLayout() = R.layout.item_list_content
}
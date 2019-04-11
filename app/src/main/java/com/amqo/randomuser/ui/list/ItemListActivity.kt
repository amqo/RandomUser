package com.amqo.randomuser.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.amqo.randomuser.R
import com.amqo.randomuser.db.entity.RandomUserEntry
import com.amqo.randomuser.internal.consume
import com.amqo.randomuser.ui.base.ScopedActivity
import com.amqo.randomuser.ui.detail.ItemDetailActivity
import com.amqo.randomuser.ui.detail.ItemDetailFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class ItemListActivity : ScopedActivity(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: RandomUserListViewModelFactory by instance()

    private lateinit var viewModel: RandomUserListViewModel

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RandomUserListViewModel::class.java)

        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (item_detail_container != null) {
            twoPane = true
        }

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        consume(viewModel.randomUsers, { randomUsers ->
            run {
                item_list.adapter = SimpleItemRecyclerViewAdapter(
                    this@ItemListActivity, randomUsers, twoPane
                )
            }
        })
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: ItemListActivity,
        private val values: List<RandomUserEntry>,
        private val twoPane: Boolean
    ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { view ->
                val item = view.tag as RandomUserEntry
                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(ItemDetailFragment.ARG_USER_ID, item.login.uuid)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(view.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_USER_ID, item.login.uuid)
                    }
                    view.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.getFullName()
            holder.contentView.text = item.email

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
            val contentView: TextView = view.content
        }
    }
}

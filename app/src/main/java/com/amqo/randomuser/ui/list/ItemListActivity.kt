package com.amqo.randomuser.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.amqo.randomuser.R
import com.amqo.randomuser.db.entity.RandomUserEntry
import com.amqo.randomuser.internal.consume
import com.amqo.randomuser.ui.base.ScopedActivity
import com.amqo.randomuser.ui.detail.ItemDetailActivity
import com.amqo.randomuser.ui.detail.ItemDetailFragment
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
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
            initRecyclerView(randomUsers.toRandomUserItems())
        })
    }

    private fun List<RandomUserEntry>.toRandomUserItems() : List<RandomUserItem> {
        return this.map {
            RandomUserItem(it)
        }
    }

    private fun initRecyclerView(items: List<RandomUserItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }
        item_list.apply {
            layoutManager = LinearLayoutManager(this@ItemListActivity)
            adapter = groupAdapter
        }
        groupAdapter.setOnItemClickListener { item, _ ->
            (item as? RandomUserItem)?.let {
                showRandomUserDetail(item)
            }
        }
    }

    private fun showRandomUserDetail(
        item: RandomUserItem
    ): Any {
        return if (twoPane) {
            val fragment = ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ItemDetailFragment.ARG_USER_ID, item.randomUserEntry.login.uuid)
                }
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, fragment)
                .commit()
        } else {
            val intent = Intent(this, ItemDetailActivity::class.java).apply {
                putExtra(ItemDetailFragment.ARG_USER_ID, item.randomUserEntry.login.uuid)
            }
            startActivity(intent)
        }
    }
}

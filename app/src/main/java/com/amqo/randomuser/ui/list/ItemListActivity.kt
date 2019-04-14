package com.amqo.randomuser.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amqo.randomuser.db.entity.RandomUserEntry
import com.amqo.randomuser.internal.afterTextChanged
import com.amqo.randomuser.internal.consume
import com.amqo.randomuser.internal.observeOnce
import com.amqo.randomuser.ui.base.ScopedActivity
import com.amqo.randomuser.ui.detail.ItemDetailActivity
import com.amqo.randomuser.ui.detail.ItemDetailFragment
import kotlinx.android.synthetic.main.activity_random_user_list.*
import kotlinx.android.synthetic.main.random_user_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class ItemListActivity : ScopedActivity(), KodeinAware, RandomUsersAdapter.RandomUsersListener {

    override val kodein by closestKodein()
    private val viewModelFactory: RandomUserListViewModelFactory by instance()

    private lateinit var viewModel: RandomUserListViewModel
    private lateinit var adapterRandomUsers: RandomUsersAdapter
    private lateinit var usersPagedList: PagedList<RandomUserEntry>

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.amqo.randomuser.R.layout.activity_random_user_list)
        setSupportActionBar(toolbar)
//        supportPostponeEnterTransition()

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RandomUserListViewModel::class.java)

        toolbar.title = title
        twoPane = item_detail_container != null

        initRandomUsersAdapter()
        initSearchListener()
    }

    // RandomUsersAdapter.RandomUsersListener functions

    override fun onRandomUserSelected(randomUser: RandomUserEntry) {
        showRandomUserDetail(randomUser)
    }

    override fun onRemoveRandomUser(randomUser: RandomUserEntry) {
        runBlocking(Dispatchers.IO) {
            viewModel.removeUser(randomUser)
        }
    }

    // Private functions

    private fun initSearchListener() {
        search.onActionViewExpanded()
        search.clearFocus()
        search.afterTextChanged {
            search.clearFocus()
            if (it.isEmpty()) {
                adapterRandomUsers.submitList(usersPagedList)
            } else {
                getFilteredRandomUsers(it)
            }
        }
    }

    private fun getFilteredRandomUsers(search: String) {
        viewModel.getFilteredRandomUsers(search).observeOnce(this@ItemListActivity,
            Observer { filteredRandomUsers ->
                adapterRandomUsers.submitList(filteredRandomUsers)
            })
    }

    private fun initRandomUsersAdapter()= launch(Dispatchers.Main) {
        val linearLayoutManager = LinearLayoutManager(
            this@ItemListActivity, RecyclerView.VERTICAL, false)
        adapterRandomUsers = RandomUsersAdapter(this@ItemListActivity)
        item_list.apply {
            layoutManager = linearLayoutManager
            adapter = adapterRandomUsers
        }
        consume(viewModel.randomUsers, {
            usersPagedList = it
            if (search.query.isEmpty()) {
                adapterRandomUsers.submitList(usersPagedList)
            } else {
                getFilteredRandomUsers(search.query.toString())
            }
        })
    }

    private fun showRandomUserDetail(
        randomUser: RandomUserEntry
    ): Any {
        return if (twoPane) {
            val fragment = ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ItemDetailFragment.ARG_USER_ID, randomUser.login.uuid)
                }
            }
            supportFragmentManager
                .beginTransaction()
//                .addSharedElement(user_image, randomUser.getId())
                .replace(com.amqo.randomuser.R.id.item_detail_container, fragment)
                .commit()
        } else {
            val intent = Intent(this, ItemDetailActivity::class.java).apply {
                putExtra(ItemDetailFragment.ARG_USER_ID, randomUser.getId())
            }
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                this, user_image as View, randomUser.getId())
//            startActivity(intent, options.toBundle())
            startActivity(intent)
        }
    }
}

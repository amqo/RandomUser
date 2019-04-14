package com.amqo.randomuser.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amqo.randomuser.R
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.internal.afterTextChanged
import com.amqo.randomuser.internal.consume
import com.amqo.randomuser.internal.hideKeyboard
import com.amqo.randomuser.internal.observeOnce
import com.amqo.randomuser.ui.base.ScopedActivity
import com.amqo.randomuser.ui.detail.RandomUserDetailActivity
import com.amqo.randomuser.ui.detail.RandomUserDetailFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_random_user_list.*
import kotlinx.android.synthetic.main.random_user_list.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class RandomUserListActivity : ScopedActivity(), KodeinAware, RandomUserListAdapter.RandomUsersListener {

    override val kodein by closestKodein()
    private val viewModelFactory: RandomUserListViewModelFactory by instance()

    private lateinit var viewModel: RandomUserListViewModel
    private lateinit var adapterRandomUserList: RandomUserListAdapter
    private lateinit var usersPagedList: PagedList<RandomUserEntry>

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_user_list)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RandomUserListViewModel::class.java)

        toolbar.title = title
        twoPane = item_detail_container != null

        initRandomUsersAdapter()
        initSearchListener()
    }

    override fun onResume() {
        super.onResume()
        search.clearFocus()
        hideKeyboard()
    }

    // RandomUserListAdapter.RandomUsersListener functions

    override fun onRandomUserSelected(randomUser: RandomUserEntry) {
        showRandomUserDetail(randomUser)
    }

    override fun onRemoveRandomUser(randomUser: RandomUserEntry) {
        search.clearFocus()
        hideKeyboard()
        Snackbar.make(frameLayout, "Please confirm, are you sure?", Snackbar.LENGTH_LONG)
            .setAction("Remove") {
                viewModel.removeUser(randomUser)
            }.show()
    }

    // Private functions

    private fun initRandomUsersAdapter() {
        val linearLayoutManager = LinearLayoutManager(
            this@RandomUserListActivity, RecyclerView.VERTICAL, false)
        adapterRandomUserList = RandomUserListAdapter(this@RandomUserListActivity)
        item_list.apply {
            layoutManager = linearLayoutManager
            adapter = adapterRandomUserList
        }
        launch {
            consume(viewModel.randomUsers, {
                usersPagedList = it
                if (search.query.isEmpty()) {
                    no_results_container.visibility = View.GONE
                    adapterRandomUserList.submitList(usersPagedList)
                } else {
                    getFilteredRandomUsers(search.query.toString())
                }
            })
        }
    }

    private fun initSearchListener() {
        search.onActionViewExpanded()
        search.clearFocus()
        search.afterTextChanged {
            if (it.isEmpty()) {
                search.clearFocus()
                no_results_container.visibility = View.GONE
                adapterRandomUserList.submitList(usersPagedList)
            } else {
                getFilteredRandomUsers(it)
            }
        }
    }

    private fun getFilteredRandomUsers(searchTerm: String) {
        viewModel.getFilteredRandomUsers(searchTerm).observeOnce(this@RandomUserListActivity,
            Observer { filteredRandomUsers ->
                adapterRandomUserList.submitList(filteredRandomUsers)
                if (filteredRandomUsers.isNotEmpty()) {
                    search.clearFocus()
                    no_results_container.visibility = View.GONE
                } else {
                    no_results_container.visibility = View.VISIBLE
                }
            })
    }

    private fun showRandomUserDetail(
        randomUser: RandomUserEntry
    ): Any {
        return if (twoPane) {
            showDetailFragment(randomUser)
        } else {
            val intent = Intent(this, RandomUserDetailActivity::class.java).apply {
                putExtra(RandomUserDetailFragment.ARG_USER_ID, randomUser.getId())
                putExtra(RandomUserDetailFragment.ARG_USER_NAME, randomUser.getFullName())
            }
            startActivity(intent)
        }
    }

    private fun showDetailFragment(
        randomUser: RandomUserEntry
    ): Int {
        val fragment = RandomUserDetailFragment().apply {
            arguments = Bundle().apply {
                putString(RandomUserDetailFragment.ARG_USER_ID, randomUser.login.uuid)
            }
        }
        return supportFragmentManager
            .beginTransaction()
            .replace(R.id.item_detail_container, fragment)
            .commit()
    }
}

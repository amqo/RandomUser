package com.amqo.randomuser.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.amqo.randomuser.R
import com.amqo.randomuser.databinding.ActivityRandomUserDetailBinding
import com.amqo.randomuser.ui.base.ScopedActivity
import com.amqo.randomuser.ui.detail.model.RandomUserDetailActivityViewModel
import com.amqo.randomuser.ui.detail.model.RandomUserDetailActivityViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_random_user_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class RandomUserDetailActivity : ScopedActivity(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: RandomUserDetailActivityViewModelFactory by instance()

    private lateinit var viewModel: RandomUserDetailActivityViewModel
    private lateinit var activityMainBinding: ActivityRandomUserDetailBinding
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_random_user_detail)

        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RandomUserDetailActivityViewModel::class.java)

        bindUI()
        if (savedInstanceState == null) {
            addDetailFragment()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            } else -> super.onOptionsItemSelected(item)
        }

    // Private functions

    private fun bindUI() {
        userId = intent.getStringExtra(RandomUserDetailFragment.ARG_USER_ID)
        activityMainBinding.randomUserName = intent.getStringExtra(RandomUserDetailFragment.ARG_USER_NAME)
        activityMainBinding.executePendingBindings()
        initFabRemoveButton()
    }

    private fun initFabRemoveButton() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, R.string.message_confirmation, Snackbar.LENGTH_LONG)
                .setAction(R.string.action_remove) {
                    launch(Dispatchers.IO) {
                        viewModel.removeUser(userId)
                    }
                    finish()
                }.show()
        }
    }

    private fun addDetailFragment() {
        val fragment = RandomUserDetailFragment().apply {
            arguments = Bundle().apply {
                putString(RandomUserDetailFragment.ARG_USER_ID, userId)
            }
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.item_detail_container, fragment).commit()
    }
}
